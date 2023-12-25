package com.example.serena

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CardMusicAdapter(private  val musicList: ArrayList<IMusicCardData>): RecyclerView.Adapter<CardMusicAdapter.MusicHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMusicAdapter.MusicHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.serena_music_card, parent, false)
        context = parent.context
        return CardMusicAdapter.MusicHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardMusicAdapter.MusicHolder, position: Int) {
        val currentItem = musicList[position]
        val image = currentItem.cover_image ?: "https://storage.googleapis.com/serena-app-storage/assets%2Fplaceholders%2Fserenbox_mockup_transparent.png"
        Glide.with(context).load(image).into(holder.image)
        var title = currentItem.title ?: ""
        if (title.length > 20) {
            title = title.substring(0, 21) + "..."
        }
        holder.title.text = title
        holder.album.text = currentItem.album
        holder.playBtn.setOnClickListener {
            Log.d("CARD", "Play Music ${currentItem.title}")
        }

    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    class MusicHolder(viewsItem: View): RecyclerView.ViewHolder(viewsItem) {
        val image: ImageView = viewsItem.findViewById(R.id.image)
        val title: TextView = viewsItem.findViewById(R.id.title)
        val album: TextView = viewsItem.findViewById(R.id.album)
        val playBtn: Button = viewsItem.findViewById(R.id.play)
    }

}