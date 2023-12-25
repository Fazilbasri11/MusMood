package com.example.serena


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class SerenBoxDetailFragment(private val mainActivity: MainActivity, private val serenID: String) : Fragment(R.layout.fragment_seren_box_detail) {
    private lateinit var btnUse: Button
    private lateinit var api: Service
    private lateinit var imageLeft: ImageView
    private lateinit var imageRight: ImageView
    private lateinit var title: TextView
    private lateinit var cardLeft: LinearLayout
    private lateinit var cardRight: LinearLayout
    private lateinit var slotALabel: TextView
    private lateinit var slotATitle: TextView
    private lateinit var capacitySlotA: TextView
    private lateinit var currentCapacitySlotA: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnUse = view.findViewById(R.id.use)
        api = Service(requireActivity())
        imageLeft = view.findViewById(R.id.image_left)
        imageRight = view.findViewById(R.id.image_right)
        title = view.findViewById(R.id.title)
        cardLeft = view.findViewById(R.id.card_left)
        cardRight = view.findViewById(R.id.card_right)
        slotALabel = view.findViewById(R.id.slot_a_label)
        slotATitle = view.findViewById(R.id.slot_a_title)
        capacitySlotA = view.findViewById(R.id.capacity_slot_a)
        currentCapacitySlotA = view.findViewById(R.id.current_capacity_slot_a)

        loadFragment()
    }


    private fun loadFragment() {
        btnUse.setOnClickListener{
            mainActivity.serenBoxConfigure()
        }

        api.getSerenBoxDetail(serenID, object : Service.ApiCallback<IResponCreatedSerenBox> {
            override fun onSuccess(res: IResponCreatedSerenBox) {
                requireActivity().runOnUiThread{

                    Log.d("IMAGE", res.slotA.toString())
                    slotALabel.text = res.slotA.label
                    slotATitle.text = res.slotA.name
                    capacitySlotA.text = res.slotA.capacity_ml.toString()
                    currentCapacitySlotA.text = res.slotA.current_capacity_ml.toString()
                    Glide.with(requireActivity()).load(res.slotA.image_url).into(imageLeft)
                    if(!res.slotA.is_active) {
                        cardLeft.alpha = 0.6f
                    }
                    Glide.with(requireActivity()).load(res.slotB.image_url).into(imageRight)
                    if(!res.slotB.is_active) {
                        cardRight.alpha = 0.6f
                    }
                    title.text = res.name
                }
            }

            override fun onFailure(error: IResponseApiError) {
                requireActivity().runOnUiThread {
                    Toast.makeText(context, "Failed Deleted Account: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

}