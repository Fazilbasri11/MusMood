package com.example.serena.data

import android.content.Context
import android.content.SharedPreferences
import com.example.serena.ISerenBoxConfiguration

class Authentication(private val context: Context) {
    // Fungsi untuk mendapatkan token dari SharedPreferences
    fun getEmail(): String? {
        val sp: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        return sp.getString("email", null)
    }
    fun getPassword(): String? {
        val sp: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        return sp.getString("password", null)
    }

    fun getToken(): String? {
        val sp: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        return sp.getString("auth", null)
    }

    fun setToken(auth: String) {
        val sp: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString("auth", auth)
        editor.apply()
    }


    fun getUserID(): String? {
        val sp: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        return sp.getString("user_id", null)
    }


    fun saveAccount(userID: String, email: String, password: String) {
        val sp: SharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString("user_id", userID)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }

    fun getSerenBoxConfiguration(): ISerenBoxConfiguration {
        val sp: SharedPreferences = context.getSharedPreferences("serenbox_configuration", Context.MODE_PRIVATE)
        val duration = sp.getString("duration", "10").toString().toInt()
        val detection_mode = sp.getString("detection_mode", "Interval").toString()
        val diffusion_option = sp.getString("diffusion_option", "Match Mood").toString()
        val configuration = ISerenBoxConfiguration(duration, detection_mode, diffusion_option)
        return configuration
    }
    fun setSerenBoxConfiguration(data: ISerenBoxConfiguration) {
        val sp: SharedPreferences = context.getSharedPreferences("serenbox_configuration", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()
        editor.putString("duration", data.duration.toString())
        editor.putString("detection_mode", data.detection_mode)
        editor.putString("diffusion_option", data.diffusion_option)
        editor.apply()
    }

}