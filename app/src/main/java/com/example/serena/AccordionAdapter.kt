package com.example.serena

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccordionAdapter(private val title: String, private val dataList: ArrayList<IAccordionData>) :
    RecyclerView.Adapter<AccordionAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var arrowIcon: ImageView = itemView.findViewById(R.id.arrow_icon)
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val contentLayout: LinearLayout = itemView.findViewById(R.id.contentLayout)
        val border_bottom: LinearLayout = itemView.findViewById(R.id.border_bottom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.acordion_configuration, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = title
        holder.titleTextView.setOnClickListener {
            toggleAccordion(holder.contentLayout, holder.border_bottom, dataList, holder.arrowIcon)
        }
    }

    private fun toggleAccordion(contentLayout: LinearLayout, border_bottom: LinearLayout, value: ArrayList<IAccordionData>, arrowIcon: ImageView) {
        if (contentLayout.visibility == View.VISIBLE) {
            contentLayout.visibility = View.GONE
            border_bottom.visibility = View.GONE
            arrowIcon.rotation = 90f
        } else {
            arrowIcon.rotation = -90f

            contentLayout.removeAllViews()

            // Mendapatkan inflater dari context
            val inflater = LayoutInflater.from(contentLayout.context)

            for (data in value) {
                val textView = inflater.inflate(R.layout.accordion_value_configuration, contentLayout, false) as TextView
                textView.text = data.value
                contentLayout.addView(textView)
            }
            border_bottom.visibility = View.VISIBLE
            contentLayout.visibility = View.VISIBLE
        }
    }
    override fun getItemCount(): Int {
        return 1
    }
}

