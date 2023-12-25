package com.example.serena


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ProductDetailFragment(private var product: ISerenProductResData) : Fragment(R.layout.fragment_product_detail) {
    private lateinit var productTitle: TextView
    private lateinit var productPrice: TextView
    private lateinit var productImage: ImageView
    private lateinit var productDescription: TextView
    private lateinit var purchaseBtn: Button
    private lateinit var serenPlaceFragment: PlaceFragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productTitle = view.findViewById(R.id.title)
        productPrice = view.findViewById(R.id.price)
        productImage = view.findViewById(R.id.image)
        productDescription = view.findViewById(R.id.description)
        purchaseBtn = view.findViewById(R.id.purchase)
        serenPlaceFragment = PlaceFragment()

        productTitle.text = product.name
        productPrice.text = product.price_idr.toString()
        Glide.with(requireActivity()).load(product.image_name).into(productImage)
        productDescription.text = product.description

        loadEvent()
    }


    private fun loadEvent() {
        purchaseBtn.setOnClickListener{
            backToSerenPlace()
        }
    }


    private fun backToSerenPlace() {
        val mainActivity = requireActivity() as MainActivity
        mainActivity.backToSerenPlace()
    }


}