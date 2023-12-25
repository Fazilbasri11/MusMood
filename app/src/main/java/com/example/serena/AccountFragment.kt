package com.example.serena

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.serena.data.Authentication
import com.google.android.material.imageview.ShapeableImageView

class AccountFragment : Fragment(R.layout.fragment_account) {

    private lateinit var auth: Authentication
    private lateinit var api: Service
    private lateinit var context: Context
    private lateinit var welcomeActivity: Intent
    private lateinit var userID: String
    private lateinit var usernameField: TextView
    private lateinit var imageField: ShapeableImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Authentication(requireActivity())
        context = requireActivity()
        api = Service(requireActivity())
        welcomeActivity = Intent(requireActivity(), WelcomeActivity::class.java)
        userID = auth.getUserID() ?: ""

        usernameField = view.findViewById(R.id.username)
        imageField = view.findViewById(R.id.image)

        val btnLogout: Button = view.findViewById(R.id.logout)
        val btnDeleted: Button = view.findViewById(R.id.delete)

        loadUserData()

        btnLogout.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            auth.setToken("")
            auth.saveAccount("", "", "")
            startActivity(intent)
        }

        btnDeleted.setOnClickListener {
            val userID = auth.getUserID() ?: ""
            if(userID == "") {
                requireActivity().runOnUiThread {
                    Toast.makeText(context, "Delete Account Failed UserID Not Found", Toast.LENGTH_SHORT).show()
                }
            } else {
                api.deletedAccount(userID, object : Service.ApiCallback<IDeletedAccountData> {
                    override fun onSuccess(status: IDeletedAccountData) {
                        auth.saveAccount("", "", "")
                        auth.setToken("")
                        Log.d("SUCCESS", "Delete Account Success")
                        startActivity(welcomeActivity)
                    }
                    override fun onFailure(message: IResponseApiError) {
                        Log.d("ERROR", "Delete Account Failed ${message.message}")
                    }
                })
            }
        }
    }


    private fun loadUserData() {
        api.getUserDetail(userID, object : Service.ApiCallback<IResponseUserDetail> {
            override fun onSuccess(res: IResponseUserDetail) {
                Log.d("Success", res.toString())
                requireActivity().runOnUiThread {
                    Glide.with(requireContext()).load(res.image_name).into(imageField)
                    usernameField.text = res.username
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

