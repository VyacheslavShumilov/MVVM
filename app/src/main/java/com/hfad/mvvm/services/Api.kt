package com.hfad.mvvm.services

import com.hfad.mvvm.model.User
import com.hfad.mvvm.model.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users")
    suspend fun getUsers(): Response<ArrayList<Users>>

    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): Response<User>

    companion object {
        var retrofitService: Api? = null
        fun getInstance(): Api {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(Api::class.java)
            }
            return retrofitService!!
        }
    }
}