package com.example.serena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import com.example.serena.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bindding: ActivityMainBinding
    lateinit var accountIcon: ImageView
    private lateinit var api: Service
    private lateinit var logo: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        api = Service(this)
        accountIcon = findViewById(R.id.navigation_account)
        logo = findViewById(R.id.toolbarNavigationIcon)

        val fragmentContainer = findViewById<FragmentContainerView>(R.id.fragment_container)
        val topbarLayout = findViewById<AppBarLayout>(R.id.topbar_layout)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        loadLayout(fragmentContainer, topbarLayout, bottomNavigation)

    }


    private fun loadLayout(fragmentContainer: FragmentContainerView, topbar_layout: AppBarLayout, bottom_navigation: BottomNavigationView) {
        val topbarHeight = topbar_layout.height // Tinggi toolbar
        val bottomNavHeight = bottom_navigation.height // Tinggi bottom navigation
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels // Tinggi layar
        val fragmentContainerHeight = screenHeight - topbarHeight - bottomNavHeight
        val params = fragmentContainer.layoutParams
        params.height = fragmentContainerHeight
        fragmentContainer.layoutParams = params

        // Button Account Menu
        val accountMenuItem = bottom_navigation.menu.findItem(R.id.navigation_account)
        accountMenuItem.isVisible = false
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()

        accountIcon.setOnClickListener {
            accountMenuItem.isChecked = true
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AccountFragment()).commit()
        }

        logo.setOnClickListener{
            val homeMenu = bottom_navigation.menu.findItem(R.id.navigation_home)
            homeMenu.isChecked = true
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }



        bottom_navigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
                }
                R.id.navigation_mood -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MoodFragment(this)).commit()
                }
                R.id.navigation_music -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MusicFragment()).commit()
                }
                R.id.navigation_seren_place -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PlaceFragment()).commit()
                }
            }
            true // Mengembalikan true menandakan bahwa item telah ditangani
        }
    }


    fun serenBoxDetail(id: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SerenBoxDetailFragment(this, id)).commit()
    }

    fun backToMoodFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MoodFragment(this)).commit()
    }

    fun serenBoxConfigure() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SerenBoxConfigurationFragment(this)).commit()
    }
    fun serenBoxSession() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SerenBoxSessionFragment(this)).commit()
        val topbarLayout = findViewById<AppBarLayout>(R.id.topbar_layout)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        topbarLayout.isVisible = false
        bottomNavigation.isVisible = false
    }

    fun backToHomeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        val topbarLayout = findViewById<AppBarLayout>(R.id.topbar_layout)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        topbarLayout.isVisible = true
        bottomNavigation.isVisible = true
    }


    fun backToEmotionCaptureResultFragment(data: IUserEmotionsResData) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, EmotionDetectionResultFragment(data, this)).commit()
    }

    fun backToPhotoCaptureFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PhotoCaptureFragment(this)).commit()
    }

    fun backToSerenPlace() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PlaceFragment()).commit()
    }

    fun backToEmotionDetectionStartFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, EmotionDetectionStartFragment(this)).commit()
    }

    fun backToPhotoEmotionCountdownFragment(imagePath: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, EmotionDetectionCountdownFragment(this, imagePath)).commit()
    }



    fun serenPlaceProductDetail(productID: String) {
        api.getSerenPlaceProductDetail(productID, object : Service.ApiCallback<ISerenProductResData>{
            override fun onSuccess(res: ISerenProductResData) {
                val productDetailFragment = ProductDetailFragment(res)
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, productDetailFragment).commit()
            }
            override fun onFailure(message: IResponseApiError) {
                Toast.makeText(this@MainActivity, "Error Fetch Api ${message.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}