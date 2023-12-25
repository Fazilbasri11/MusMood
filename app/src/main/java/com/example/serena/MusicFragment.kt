package com.example.serena


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MusicFragment : Fragment(R.layout.fragment_music) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicList: ArrayList<IMusicCardData>
    private lateinit var api: Service
    private lateinit var context: Context


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = requireActivity()
        api = Service(context as FragmentActivity)
        musicList = arrayListOf<IMusicCardData>()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val btnRecommended: Button = view.findViewById(R.id.recommended)
        btnRecommended.setOnClickListener{
            loadMusic()
        }
        loadMusic()
    }

    private fun loadMusic() {
        api.getMusicSeren(0.7f, 0.3f, object : Service.ApiCallback<ArrayList<IMusicCardData>> {
            override fun onSuccess(data: ArrayList<IMusicCardData>) {
                musicList.clear()
                for (i in data.indices) {
                    val card = data[i]
                    val cardData = IMusicCardData(
                        card.id,
                        card.title,
                        card.artist,
                        card.album,
                        card.release_year,
                        card.cover_image,
                        card.preview_link
                    )
                    musicList.add(cardData)
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
        loadMusic()
    }


    fun loadCard() {
        recyclerView.adapter = CardMusicAdapter(musicList)
    }


}