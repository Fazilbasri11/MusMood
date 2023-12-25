package com.example.serena


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible


class EmotionDetectionFragment(private val mainActivity: MainActivity) : Fragment(R.layout.fragment_emotion_detection) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emotionDetectionStart = EmotionDetectionStartFragment(mainActivity)
        loadFragment(emotionDetectionStart)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

