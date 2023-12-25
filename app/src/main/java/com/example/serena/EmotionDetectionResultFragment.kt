package com.example.serena


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class EmotionDetectionResultFragment(private val data: IUserEmotionsResData, private val mainActivity: MainActivity) : Fragment(R.layout.fragment_emotion_detection_result) {
    private lateinit var timeField: TextView
    private lateinit var backBtn: Button
    private val serenUtils: SerenUtils = SerenUtils()

    private lateinit var neutral: TextView
    private lateinit var joy: TextView
    private lateinit var sadness: TextView
    private lateinit var disgus: TextView
    private lateinit var suprise: TextView
    private lateinit var anger: TextView
    private lateinit var fear: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image: ImageView = view.findViewById(R.id.image)
        timeField = view.findViewById(R.id.time)
        backBtn = view.findViewById(R.id.back)

        neutral = view.findViewById(R.id.neutral)
        joy = view.findViewById(R.id.joy)
        sadness = view.findViewById(R.id.sadness)
        disgus = view.findViewById(R.id.disgust)
        suprise = view.findViewById(R.id.suprise)
        anger = view.findViewById(R.id.anger)
        fear = view.findViewById(R.id.fear)

        Glide.with(requireActivity()).load(data.user_photo).into(image)
        backBtn.setOnClickListener{
            mainActivity.backToMoodFragment()
        }
        loadData()
    }

    private fun loadData() {
        timeField.text = "${serenUtils.getDayNameFromDate(data.created_time).subSequence(0, 3)} ${serenUtils.getFormattedDate(data.created_time)} - ${serenUtils.getFormattedTime(data.created_time)}"
        neutral.text = data.relax.neutral.toString() + "%"
        joy.text = data.relax.joy.toString() + "%"
        sadness.text = data.relax.sadness.toString() + "%"
        disgus.text = data.relax.disgust.toString() + "%"
        suprise.text = data.energetic.surprise.toString() + "%"
        anger.text = data.energetic.anger.toString() + "%"
        fear.text = data.energetic.fear.toString() + "%"
    }
}