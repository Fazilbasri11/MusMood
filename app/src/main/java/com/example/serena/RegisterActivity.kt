package com.example.serena

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.serena.data.Authentication

class RegisterActivity : AppCompatActivity() {
    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var api: Service
    private  lateinit var auth: Authentication
    private lateinit var mainActivity: Intent
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        api = Service(this)
        auth = Authentication(this)

        context = this
        mainActivity = Intent(this, MainActivity::class.java)

        usernameInput = findViewById(R.id.username)
        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)

        val btnBack: Button = findViewById(R.id.back)
        val btnRegister: Button = findViewById(R.id.register)
        btnBack.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener{
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            api.register(username, email, password, object : Service.ApiCallback<ILoginResponseApi> {
                override fun onSuccess(status: ILoginResponseApi) {
                    auth.saveAccount(status.userId , email, password)
                    auth.setToken(status.accessToken)
                    startActivity(mainActivity)
                }
                override fun onFailure(message: IResponseApiError) {
                    runOnUiThread {
                        Toast.makeText(context, "Login Gagal ${message.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}