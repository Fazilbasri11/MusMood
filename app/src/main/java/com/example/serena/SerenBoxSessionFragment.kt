package com.example.serena


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.serena.data.Authentication
import java.io.File
import java.util.concurrent.ExecutorService
import kotlin.random.Random

class SerenBoxSessionFragment(private val mainActivity: MainActivity) : Fragment(R.layout.fragment_seren_box_sesssion) {
    private lateinit var stopBtn: Button
    private lateinit var countDownMinutes: TextView
    private lateinit var countDownSeconds: TextView
    private lateinit var auth: Authentication

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var context: Context

    val handler = android.os.Handler(Looper.getMainLooper())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stopBtn = view.findViewById(R.id.stop)
        auth = Authentication(requireActivity())
        context = requireContext()

        countDownMinutes = view.findViewById(R.id.countdown_minutes)
        countDownSeconds = view.findViewById(R.id.countdown_seconds)


        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PhotoCaptureFragment.REQUIRED_PERMISSIONS,
                PhotoCaptureFragment.REQUEST_CODE_PERMISSIONS
            )
        }

        loadFragment()

    }

    private fun loadFragment() {
        stopBtn.setOnClickListener{
            mainActivity.backToMoodFragment()
        }
        val configuration = auth.getSerenBoxConfiguration()
        startCountDown(configuration.duration)
    }


    private fun allPermissionsGranted() = PhotoCaptureFragment.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun  startCountDown(duration: Int) {
        val minutesValue: Int = duration
        val secondValue: Int = countDownSeconds.text.toString().toInt()
        if(minutesValue > 0) {
            if(secondValue > 0) {
                countDownSeconds.text = (secondValue - 1).toString()
            } else {
                countDownMinutes.text = (minutesValue - 1).toString()
                countDownSeconds.text = (60 - 1).toString()
            }
            handler.postDelayed({
                startCountDown(minutesValue)
            }, 1000)
        } else {
            Log.d("SUCCESS", "TASK DONE")
        }
    }

    fun takePhoto() {
        // Pastikan imageCapture sudah diinisialisasi sebelum digunakan di sini
        val imageCapture = imageCapture ?: return

        val photoFile = File(requireActivity().externalMediaDirs.firstOrNull(), "${generateRandomString(12)}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    val fileJenny = File("/storage/emulated/0/jenny.jpg")
//                    mainActivity.backToPhotoEmotionCountdownFragment(fileJenny.absolutePath)
                    Log.d("DEBUG_TAKE_PICTURE", photoFile.absolutePath)
                    handler.postDelayed({
                        takePhoto() // Panggil takePhoto di dalam lambda postDelayed
                    }, 5000)
                }
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Error taking photo: ${exception.message}", Toast.LENGTH_SHORT).show()
                    handler.postDelayed({
                        takePhoto() // Panggil takePhoto di dalam lambda postDelayed
                    }, 5000)
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
            handler.postDelayed({
                takePhoto() // Panggil takePhoto di dalam lambda postDelayed
            }, 5000)
        }, ContextCompat.getMainExecutor(context))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA // Menggunakan kamera depan

        val imageCapture = ImageCapture.Builder().build()
        this.imageCapture = imageCapture

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                // Handle if permissions are not granted by showing a toast or something similar
            }
        }

        // Call super implementation
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun generateRandomString(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') // Pilihan karakter yang ingin digunakan
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }


    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


}