package com.example.serena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import com.example.serena.data.Authentication

class SplashActivity : AppCompatActivity() {

    private  lateinit var api: Service
    private lateinit var auth: Authentication
    private val handler = android.os.Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        api = Service(this)
        auth = Authentication(this)
        checkUserStatus()
    }

    override fun onResume() {
        super.onResume()
        checkUserStatus()
    }

    fun checkUserStatus() {
        val welcome = Intent(this, WelcomeActivity::class.java)
        val home = Intent(this, MainActivity::class.java)
        val login = Intent(this, LoginActivity::class.java)

        val email = auth.getEmail()
        val password = auth.getPassword()

        if (email == null || password == null) {
            handler.postDelayed({
                startActivity(welcome)
            }, 1000)
        } else {
            api.login(email, password, object : Service.ApiCallback<ILoginResponseApi> {
                override fun onSuccess(status: ILoginResponseApi) {
                    auth.saveAccount(status.userId, email, password)
                    auth.setToken(status.accessToken)
                    startActivity(home)
                }
                override fun onFailure(message: IResponseApiError) {
                    Toast.makeText(this@SplashActivity, message.message, Toast.LENGTH_SHORT).show()
                    handler.postDelayed({
                        startActivity(login)
                    }, 1000)
                }
            })
        }
    }

}