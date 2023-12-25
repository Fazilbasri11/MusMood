package com.example.serena

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DeviceAdapter(private  val deviceList: ArrayList<IDeviceSerenBox>, private val mainActivity: MainActivity):
    RecyclerView.Adapter<DeviceAdapter.DeviceHolder>() {
    private lateinit var context: Context
    private lateinit var viewDevice: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        viewDevice = LayoutInflater.from(parent.context).inflate(R.layout.serena_card, parent, false)
        context = parent.context
        return DeviceHolder(viewDevice)
    }

    override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
        val currentItem = deviceList[position]
        val image = currentItem.image_name ?: "https://storage.googleapis.com/serena-app-storage/assets%2Fplaceholders%2Fserenbox_mockup_transparent.png"
        Glide.with(context).load(image).into(holder.image)
        holder.title.text = currentItem.name


        viewDevice.setOnClickListener{
            mainActivity.serenBoxDetail(currentItem.id)
        }


    }

    override fun getItemCount(): Int {
        return deviceList.size
    }




    class DeviceHolder(viewsItem: View): RecyclerView.ViewHolder(viewsItem) {

        val title: TextView = viewsItem.findViewById(R.id.seren_card_title)
        val image: ImageView = viewsItem.findViewById(R.id.seren_image)

    }


}