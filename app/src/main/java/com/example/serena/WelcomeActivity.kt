package com.example.serena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.serena.data.Authentication

class WelcomeActivity : AppCompatActivity() {
    private lateinit var authentication: Authentication
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        authentication = Authentication(this)
        btnLogin = findViewById(R.id.login)
        btnRegister = findViewById(R.id.register)
        startApp()
    }
    private  fun startApp() {
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}