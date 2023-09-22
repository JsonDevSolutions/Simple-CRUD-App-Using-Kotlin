package com.example.viewmodel.ui.theme

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodel.ApiInterface
import com.example.viewmodel.BASE_URL
import com.example.viewmodel.LoginData
import com.example.viewmodel.LoginRequest
import com.example.viewmodel.LoginResponse
import com.example.viewmodel.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthViewModel: ViewModel() {
    private var _loginResponse = MutableLiveData<LoginResponse?>()
    private var _signUpResponse = MutableLiveData<User?>()
    private var _accessToken = MutableLiveData<String?>()

    val loginResponse: MutableLiveData<LoginResponse?>
        get() = _loginResponse

    val signUpResponse: MutableLiveData<User?>
        get() = _signUpResponse

    val accessToken: MutableLiveData<String?>
        get() = _accessToken

    fun updateUserData(loginData: LoginData) {
        _accessToken.value = loginData.access_token
    }

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.login(loginRequest)
        retrofitData.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (responseBody !== null){
                    _loginResponse.value = responseBody
                } else {
                    _loginResponse.value = null
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("AuthActivity", "onFailure: " + t.message)
            }
        })
    }

    fun signUp(user: User) {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.signUp(user)
        retrofitData.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val responseBody = response.body()
                if (responseBody !== null){
                    _signUpResponse.value = responseBody
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("AuthActivity", "onFailure: " + t.message)
            }
        })
    }
}