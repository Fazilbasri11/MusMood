package com.example.serena


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serena.data.Authentication

class SerenBoxConfigurationFragment(private val mainActivity: MainActivity) :
    Fragment(R.layout.fragment_seren_box_configuration) {

    private lateinit var detection_mode: RecyclerView
    private lateinit var diffusion_option: RecyclerView
    private lateinit var detectionModeAdapter: AccordionAdapter
    private lateinit var diffusionOptionAdapter: AccordionAdapter
    private lateinit var btnStart: Button
    private lateinit var auth: Authentication
    private lateinit var durationField: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Authentication(requireActivity())

        detection_mode = view.findViewById(R.id.detection_mode)
        diffusion_option = view.findViewById(R.id.diffusion_option)
        durationField = view.findViewById(R.id.duration)
        btnStart = view.findViewById(R.id.start)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val configuration = auth.getSerenBoxConfiguration()

        durationField.setText(configuration.duration.toString())

        // Data untuk detection mode
        val dataListDetectionMode = ArrayList<IAccordionData>()
        dataListDetectionMode.add(IAccordionData("Interval", "Interval"))
        dataListDetectionMode.add(IAccordionData("Once", "Once2"))

        // Data untuk diffusion option
        val diffusionOptionData =  ArrayList<IAccordionData>()
        diffusionOptionData.add(IAccordionData("Match Mood", "Match Mood"))
        diffusionOptionData.add(IAccordionData("Opposite Of Mood", "Opposite Of Mood"))
        diffusionOptionData.add(IAccordionData("Automatic", "Automatic"))

        // Inisialisasi adapter untuk masing-masing RecyclerView
        detectionModeAdapter = AccordionAdapter(configuration.detection_mode, dataListDetectionMode)
        diffusionOptionAdapter = AccordionAdapter(configuration.diffusion_option, diffusionOptionData)

        // Mengatur adapter dan layout manager untuk detection_mode RecyclerView
        detection_mode.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = detectionModeAdapter
            isNestedScrollingEnabled = false
        }

        // Mengatur adapter dan layout manager untuk diffusion_option RecyclerView
        diffusion_option.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = diffusionOptionAdapter
            isNestedScrollingEnabled = false
        }

        btnStart.setOnClickListener{
            val serenBoxConfigData = ISerenBoxConfiguration(durationField.text.toString().toInt(), configuration.detection_mode, configuration.diffusion_option)
            auth.setSerenBoxConfiguration(serenBoxConfigData)
            mainActivity.serenBoxSession()
        }

    }


}