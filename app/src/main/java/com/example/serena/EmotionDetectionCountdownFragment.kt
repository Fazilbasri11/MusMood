package com.example.serena


import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class EmotionDetectionCountdownFragment(private val mainActivity: MainActivity, private val imagePath: String) : Fragment(R.layout.fragment_emotion_detection_coutdown) {
    private lateinit var countDown: TextView
    private val handler = android.os.Handler(Looper.getMainLooper())
    private lateinit var api: Service
    private var apiStatusSuccess: Boolean = false

    private var count: Int = 6

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countDown = view.findViewById(R.id.count)
        api = Service(requireActivity())

        api.emotionDetector(imagePath, object : Service.ApiCallback<IUserEmotionsResData> {
            override fun onSuccess(res: IUserEmotionsResData) {
                requireActivity().runOnUiThread {
                    if(!apiStatusSuccess) {
                        apiStatusSuccess = true
                        mainActivity.backToEmotionCaptureResultFragment(res)
                    }
                }
            }
            override fun onFailure(error: IResponseApiError) {
                requireActivity().runOnUiThread {
                    if(!apiStatusSuccess) {
                        apiStatusSuccess = true
                        Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        mainActivity.backToEmotionDetectionStartFragment()
                    }
                }
            }

        })
        updateCount()

    }






    private fun updateCount() {
        count -= 1
        countDown.text = count.toString()
        if(count > 0) {
            handler.postDelayed({
                updateCount()
            }, 1000)
        } else {
            if(!apiStatusSuccess) {
                apiStatusSuccess = true
                Toast.makeText(context, "Error: Server Response Timeout", Toast.LENGTH_SHORT).show()
                mainActivity.backToEmotionDetectionStartFragment()
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
