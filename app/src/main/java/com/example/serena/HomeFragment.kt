package com.example.serena

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var pairDeviceBtn: Button
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<IDeviceSerenBox>
    private lateinit var api: Service


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pairDeviceBtn = view.findViewById(R.id.pair_device)
        api = Service(requireActivity())

        pairDeviceBtn.setOnClickListener {
            val modalFragment = DialogPairDevice()

            modalFragment.setOnDismissListener {
                onResume()
            }

            modalFragment.show(childFragmentManager, "ModalFragmentTag")
        }

        newRecyclerView = view.findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<IDeviceSerenBox>()

    }



    private fun getDeviceData() {
        api.getDeviceSerenBox(object : Service.ApiCallback<ArrayList<IDeviceSerenBox>> {
            override fun onSuccess(data: ArrayList<IDeviceSerenBox>) {
                newArrayList.clear() // Bersihkan list sebelum menambahkan data baru
                for (i in data.indices) {
                    val card = data[i]
                    val cardData = IDeviceSerenBox(card.id, card.credentials, card.ip_address, card.name, card.userId, card.image_name, card.added, card.slotAId, card.slotBId)
                    newArrayList.add(cardData)
                }
                activity?.runOnUiThread {
                    loadCard()
                }
            }

            override fun onFailure(message: IResponseApiError) {
                Log.d("FAILURE", message.message)
                Toast.makeText(context, message.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getDeviceData()
    }



    fun loadCard() {
        val mainActivityInstance = requireActivity() as MainActivity
        newRecyclerView.adapter = DeviceAdapter(newArrayList, mainActivityInstance)
    }




}
