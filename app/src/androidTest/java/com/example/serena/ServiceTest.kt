package com.example.serena

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.serena.data.Authentication
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class ServiceTest {

    private lateinit var auth: Authentication
    private lateinit var service: Service
    private lateinit var userTestData: IUserTestData
    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        auth = Authentication(context)
        service = Service(context)
        userTestData = IUserTestData("username_test", "email.test@test.com", "password_test")
    }

//    @After
//    fun tearDown() {
//        val userID = auth.getUserID() ?: ""
//        assertTrue(userID != "")
//        if(userID != "") {
//            service.deletedAccount(userID, object : Service.ApiCallback<IDeletedAccountData> {
//                override fun onSuccess(res: IDeletedAccountData) {
//                    assertTrue(res != null)
//                }
//                override fun onFailure(error: IResponseApiError) {
//                    fail(error.message)
//                }
//            })
//        }
//    }


//    @Test
//    fun register() {
//        if(auth != null) {
//            service.register(userTestData.username, userTestData.email, userTestData.password, object : Service.ApiCallback<ILoginResponseApi> {
//                override fun onSuccess(res: ILoginResponseApi) {
//                    Log.d("USER_ID_TEST", res.userId)
//                    Log.d("ACCESS_TOKEN_TEST", res.accessToken)
//                    auth.saveAccount(res.userId, userTestData.email, userTestData.password)
//                    assertTrue(res != null)
//                }
//                override fun onFailure(error: IResponseApiError) {
//                    fail(error.message)
//                }
//            })
//        }
//    }


//
//    @Test
//    fun getUserDetail() {
//    }
//
//    @Test
//    fun deletedAccount() {
//    }
//
//    @Test
//    fun emotionDetector() {
//    }
//
//    @Test
//    fun getDeviceSerenBox() {
//    }
//
//    @Test
//    fun getSerenBoxDetail() {
//    }
//
//    @Test
//    fun addSerenBox() {
//    }
//
//    @Test
//    fun getProductsSeren() {
//    }
//
//    @Test
//    fun getSerenPlaceProductDetail() {
//    }
//
//    @Test
//    fun getMusicSeren() {
//    }
//
//    @Test
//    fun getUserEmotionsList() {
//    }
}