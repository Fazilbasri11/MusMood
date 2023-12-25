package com.example.serena


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import kotlin.random.Random


class PhotoCaptureFragment(private val mainActivity: MainActivity) : Fragment(R.layout.fragment_photo_capture) {
    private lateinit var previewView: PreviewView
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null
    private lateinit var context: Context
    private val handler = android.os.Handler(Looper.getMainLooper())





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = requireContext()
        previewView = view.findViewById(R.id.preview_view)
        cameraExecutor = Executors.newSingleThreadExecutor()


        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }





    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
            handler.postDelayed({
                takePhoto() // Panggil takePhoto di dalam lambda postDelayed
            }, 1000)
        }, ContextCompat.getMainExecutor(context))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()

        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA // Menggunakan kamera depan

        preview.setSurfaceProvider(previewView.surfaceProvider)

        val imageCapture = ImageCapture.Builder().build()
        this.imageCapture = imageCapture

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
        } catch (ex: Exception) {
            ex.printStackTrace()
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
                    mainActivity.backToPhotoEmotionCountdownFragment(photoFile.absolutePath)
                }
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(context, "Error taking photo: ${exception.message}", Toast.LENGTH_SHORT).show()
                    mainActivity.backToEmotionDetectionStartFragment()
                }
            }
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
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
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


}