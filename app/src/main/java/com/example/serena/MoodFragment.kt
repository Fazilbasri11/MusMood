package com.example.serena

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serena.data.Authentication
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoodFragment(private val mainActivity: MainActivity) : Fragment(R.layout.fragment_mood) {


    // Inisialisasi variabel yang diperlukan
    private lateinit var btnDetectEmotion: Button
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var api: Service
    private lateinit var auth: Authentication
    private lateinit var moodHistory: LinearLayout
    private lateinit var moodHistoryData: ArrayList<IUserEmotionsResData>


    private lateinit var anger: TextView
    private lateinit var fear: TextView
    private lateinit var surprise: TextView
    private lateinit var disgust: TextView
    private lateinit var joy: TextView
    private lateinit var neutral: TextView
    private lateinit var sadness: TextView

    private lateinit var emotionHistoryBox: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi tampilan
        btnDetectEmotion = view.findViewById(R.id.detect_emotion)
        bottomNavigation = requireActivity().findViewById(R.id.bottom_navigation)
        api = Service(requireActivity())
        auth = Authentication(requireActivity())

        moodHistory = view.findViewById(R.id.mood_history)
        emotionHistoryBox = view.findViewById(R.id.recyclerView)

        neutral = view.findViewById(R.id.neutral)
        joy = view.findViewById(R.id.joy)
        sadness = view.findViewById(R.id.sadness)
        disgust = view.findViewById(R.id.disgust)
        surprise = view.findViewById(R.id.suprise)
        anger = view.findViewById(R.id.anger)
        fear = view.findViewById(R.id.fear)

        // Panggil fungsi loadFragment saat tombol diklik
        btnDetectEmotion.setOnClickListener {
            // Ganti fragmen ketika tombol diklik
            val emotionDetectionFragment = EmotionDetectionFragment(mainActivity)
            replaceFragment(emotionDetectionFragment)
        }

        emotionHistoryBox = view.findViewById(R.id.recyclerView)
        emotionHistoryBox.layoutManager = LinearLayoutManager(requireActivity())
        emotionHistoryBox.setHasFixedSize(true)
        moodHistoryData = arrayListOf<IUserEmotionsResData>()
        loadData()
    }

    private fun loadData() {
        val userID = auth.getUserID() ?: ""
        api.getUserEmotionsList(userID, object : Service.ApiCallback<ArrayList<IUserEmotionsResData>> {
            override fun onSuccess(res: ArrayList<IUserEmotionsResData>) {
                Log.d("Error", res.size.toString())
                mainActivity.runOnUiThread {
                    if (res.size > 0) {
                        val emotion = res[0]
                        neutral.text = emotion.relax.neutral.toString()
                        joy.text = emotion.relax.joy.toString()
                        sadness.text = emotion.relax.sadness.toString()
                        disgust.text = emotion.relax.disgust.toString()
                        surprise.text = emotion.energetic.surprise.toString()
                        anger.text = emotion.energetic.anger.toString()
                        fear.text = emotion.energetic.fear.toString()

                        moodHistoryData.clear()
                        moodHistoryData = res

                        requireActivity().runOnUiThread{
                            createMoodCardHistory()
                        }

                    } else {
                        moodHistory.isVisible = false
                    }
                }
            }

            override fun onFailure(message: IResponseApiError) {
                mainActivity.runOnUiThread {
                    Log.d("Error", message.message)
                }
            }
        })
    }

    private fun createMoodCardHistory() {
        emotionHistoryBox.adapter = MoodCardAdapter(moodHistoryData)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    // Fungsi untuk mengganti fragmen di wadah yang sesuai
    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null) // Jika diperlukan, untuk kembali ke fragmen sebelumnya
        transaction.commit()
    }
}


