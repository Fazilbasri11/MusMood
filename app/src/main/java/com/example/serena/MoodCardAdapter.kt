package com.example.serena

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoodCardAdapter(private val moodList: ArrayList<IUserEmotionsResData>): RecyclerView.Adapter<MoodCardAdapter.MoodCardHolder>() {
    private lateinit var context: Context
    private lateinit var itemView: View
    private lateinit var serenUtils: SerenUtils

    class MoodCardHolder(viewsItem: View): RecyclerView.ViewHolder(viewsItem) {
        val dayName = viewsItem.findViewById<TextView>(R.id.dayName)
        val dateValue = viewsItem.findViewById<TextView>(R.id.date_value)
        val timeValue = viewsItem.findViewById<TextView>(R.id.time_value)
        val relaxNeutral = viewsItem.findViewById<TextView>(R.id.relax_neutral)
        val relaxJoy = viewsItem.findViewById<TextView>(R.id.relax_joy)
        val relaxSadness = viewsItem.findViewById<TextView>(R.id.relax_sadness)
        val relaxDisgust = viewsItem.findViewById<TextView>(R.id.relax_disgust)
        val energeticSuprise = viewsItem.findViewById<TextView>(R.id.energetic_suprise)
        val energeticAnger = viewsItem.findViewById<TextView>(R.id.energetic_anger)
        val energeticFear = viewsItem.findViewById<TextView>(R.id.energetic_fear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodCardHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.mood_card, parent, false)
        context = parent.context
        serenUtils = SerenUtils()
        return MoodCardHolder(itemView)
    }

    override fun getItemCount(): Int {
        return moodList.size
    }

    override fun onBindViewHolder(holder: MoodCardHolder, position: Int) {
        val currentItem = moodList[position]

        holder.dayName.text = serenUtils.getDayNameFromDate(currentItem.created_time).substring(0, 3)
        holder.dateValue.text = serenUtils.getFormattedDate(currentItem.created_time)
        holder.timeValue.text = serenUtils.getFormattedTime(currentItem.created_time)

        holder.relaxNeutral.text = currentItem.relax.neutral.toString()
        holder.relaxJoy.text = currentItem.relax.joy.toString()
        holder.relaxSadness.text = currentItem.relax.sadness.toString()
        holder.relaxDisgust.text = currentItem.relax.disgust.toString()
        holder.energeticSuprise.text = currentItem.energetic.anger.toString()
        holder.energeticAnger.text = currentItem.energetic.anger.toString()
        holder.energeticFear.text = currentItem.energetic.fear.toString()


    }
}