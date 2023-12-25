package com.example.serena

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val productList: ArrayList<ISerenProductResData>,private val mainActivity: MainActivity): RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    private lateinit var context: Context
    private lateinit var itemView: View
    private val serenUtils: SerenUtils = SerenUtils()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.serena_product_card, parent, false)
        context = parent.context
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val currentItem = productList[position]
        val image = currentItem.image_name
        Glide.with(context).load(image).into(holder.image)

        itemView.setOnClickListener{
            mainActivity.serenPlaceProductDetail(currentItem.id)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductHolder(viewsItem: View): RecyclerView.ViewHolder(viewsItem) {
        val image: ImageView = viewsItem.findViewById(R.id.image)
    }

}

