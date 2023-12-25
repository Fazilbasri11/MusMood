package com.example.serena

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.serena.data.Authentication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class Service(private val context: Context) {
    private val BASE_URL: String = "https://serena-backend-2g6tjw7nja-et.a.run.app"
    private var client: OkHttpClient
    private val auth = Authentication(context)



    private val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.d("OkHttp", message) // Logging pesan dari interceptor
        }
    })

    init {
        this.client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    interface ApiCallback<T> {
        fun onSuccess(res: T)
        fun onFailure(error: IResponseApiError)
    }

    fun login(email: String, password: String, callback: ApiCallback<ILoginResponseApi>) {
        val mediaType = "application/json; charset=utf-8".toMediaType()

        val requestBody = ILoginRequestApi(email, password)
        val jsonObject = JSONObject()
        jsonObject.put("email", requestBody.email)
        jsonObject.put("password", requestBody.password)

        val requestBodyString = jsonObject.toString()

        val request = Request.Builder()
            .url("https://serena-backend-2g6tjw7nja-et.a.run.app/users/login")
            .post(RequestBody.create(mediaType, requestBodyString))
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, ILoginResponseApi::class.java)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }

        })
    }

    fun register(username: String, email: String, password: String, callback: ApiCallback<ILoginResponseApi>) {
        val path: String = "/users"
        val mediaType = "application/json; charset=utf-8".toMediaType()

        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        val requestBodyString = jsonObject.toString()

        val request = Request.Builder()
            .url(BASE_URL + path)
            .post(RequestBody.create(mediaType, requestBodyString))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, ILoginResponseApi::class.java)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }

    fun getUserDetail(userID: String, callback: ApiCallback<IResponseUserDetail>) {
        val path: String = "/users/" + userID
        val request = Request.Builder()
            .url(BASE_URL + path)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseUserDetail::class.java)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }


    fun deletedAccount(userID: String, callback: ApiCallback<IDeletedAccountData>) {
        val path: String = "/users/" + userID
        val request = Request.Builder()
            .url(BASE_URL + path)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .delete()
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IDeletedAccountData::class.java)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }


    fun emotionDetector(photoUri: String, callback: ApiCallback<IUserEmotionsResData>) {
        val url: String = BASE_URL + "/users/"+ auth.getUserID() +"/emotions/detect"
        val mediaType = "image/jpeg".toMediaType()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                "photo.jpg",
                File(photoUri!!).asRequestBody(mediaType)
            )
            .build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IUserEmotionsResData::class.java)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }

    fun getDeviceSerenBox(callback: ApiCallback<ArrayList<IDeviceSerenBox>>) {
        val url: String = BASE_URL + "/devices/serenbox"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData: ArrayList<IDeviceSerenBox> = gson.fromJson(responseData, object : TypeToken<ArrayList<IDeviceSerenBox>>() {}.type)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }


    fun getSerenBoxDetail(id: String, callback: ApiCallback<IResponCreatedSerenBox>) {
        val url: String = BASE_URL + "/devices/serenbox/" + id
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData: IResponCreatedSerenBox = gson.fromJson(responseData, object : TypeToken<IResponCreatedSerenBox>() {}.type)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }

    fun addSerenBox(name: String, credentials: String, callback: ApiCallback<IResponCreatedSerenBox>) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val url = BASE_URL + "/devices/serenbox"
        val jsonObject = JSONObject()
        jsonObject.put("credentials", credentials)
        jsonObject.put("name", name)
        val requestBodyString = jsonObject.toString()

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .post(RequestBody.create(mediaType, requestBodyString))
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponCreatedSerenBox::class.java)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }

        })
    }

    fun getProductsSeren(callback: ApiCallback<ArrayList<ISerenProductResData>>) {
        val url: String = BASE_URL + "/serenplace"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData: ArrayList<ISerenProductResData> = gson.fromJson(responseData, object : TypeToken<ArrayList<ISerenProductResData>>() {}.type)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }


    fun getSerenPlaceProductDetail(id: String, callback: ApiCallback<ISerenProductResData>) {
        val url: String = BASE_URL + "/serenplace/"+ id
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData: ISerenProductResData = gson.fromJson(responseData, object : TypeToken<ISerenProductResData>() {}.type)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }

    fun getMusicSeren(energetic: Float?, relax: Float?, callback: ApiCallback<ArrayList<IMusicCardData>>) {
        val energeticValue = energetic ?: 0.7
        val relaxValue = relax ?: 0.3
        val url: String = BASE_URL + "/music-recommender?energetic=${energeticValue}&relax=${relaxValue}"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData: ArrayList<IMusicCardData> = gson.fromJson(responseData, object : TypeToken<ArrayList<IMusicCardData>>() {}.type)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }

    fun getUserEmotionsList(userID: String, callback: ApiCallback<ArrayList<IUserEmotionsResData>>) {
        val url: String = BASE_URL + "/users/$userID/emotions"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${auth.getToken()}")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = e.message ?: "Failed to execute the request"
                val resErr = IResponseApiError(message, 500)
                callback.onFailure(resErr)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val gson = Gson()
                    val resData: ArrayList<IUserEmotionsResData> = gson.fromJson(responseData, object : TypeToken<ArrayList<IUserEmotionsResData>>() {}.type)
                    callback.onSuccess(resData)
                } else {
                    val gson = Gson()
                    val resData = gson.fromJson(responseData, IResponseApiError::class.java)
                    callback.onFailure(resData)
                }
            }
        })
    }


}