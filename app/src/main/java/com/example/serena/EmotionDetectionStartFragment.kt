package com.example.serena


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.runBlocking
import java.io.File


class EmotionDetectionStartFragment(private val mainActivity: MainActivity) : Fragment(R.layout.fragment_emotion_detection_start) {
    private lateinit var btnStart: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStart = view.findViewById(R.id.start)
        btnStart.setOnClickListener {
            mainActivity.backToPhotoCaptureFragment()
        }
    }
}