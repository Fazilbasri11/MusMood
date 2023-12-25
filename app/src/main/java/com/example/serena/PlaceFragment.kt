package com.example.serena


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PlaceFragment : Fragment(R.layout.fragment_place) {
    private lateinit var context: Context
    private lateinit var api: Service

    private lateinit var recyclerViewOil: RecyclerView
    private lateinit var recyclerViewDevice: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productListOil: ArrayList<ISerenProductResData> = ArrayList()
    private val productListDevice: ArrayList<ISerenProductResData> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = requireActivity()
        api = Service(context as FragmentActivity)
        recyclerViewOil = view.findViewById(R.id.oil_box)
        recyclerViewDevice= view.findViewById(R.id.device_box)
        loadProduct()
    }

    private fun loadProduct() {
        val apiCallback = object : Service.ApiCallback<ArrayList<ISerenProductResData>> {
            override fun onSuccess(data: ArrayList<ISerenProductResData>) {
                productListOil.clear()
                productListDevice.clear()

                for (product in data) {
                    if (product.type == "OIL") {
                        productListOil.add(product)
                    } else if (product.type == "DEVICE") {
                        productListDevice.add(product)
                    }
                }
                loadCardOil(productListOil)
                loadCardDevice(productListDevice)
            }

            override fun onFailure(message: IResponseApiError) {
                Log.e("API_REQUEST", "Failed to load products: ${message.message}")
                activity?.runOnUiThread {
                    Toast.makeText(context, "Failed to load products: ${message.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        api.getProductsSeren(apiCallback)
    }

    private fun loadCardOil(products: ArrayList<ISerenProductResData>) {
        activity?.runOnUiThread {
            val mainActivityInstance = requireActivity() as MainActivity
            productAdapter = ProductAdapter(products, mainActivityInstance)
            recyclerViewOil.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewOil.adapter = productAdapter
            productAdapter.notifyDataSetChanged()
        }
    }
    private fun loadCardDevice(products: ArrayList<ISerenProductResData>) {
        activity?.runOnUiThread {
            val mainActivityInstance = requireActivity() as MainActivity
            productAdapter = ProductAdapter(products, mainActivityInstance)
            recyclerViewDevice.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewDevice.adapter = productAdapter
            productAdapter.notifyDataSetChanged()
        }
    }
}