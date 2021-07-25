package com.example.noodoeassignment.api

import com.example.noodoeassignment.BuildConfig
import com.example.noodoeassignment.model.entity.LoginResponse
import com.example.noodoeassignment.model.entity.UpdateUserResponse
import com.example.noodoeassignment.model.repository.UpdateUserRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    companion object {
        var apiINterface: ApiInterface? = null
        fun getInstance() : ApiInterface {
            if (apiINterface == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("BuildConfig")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build()

                apiINterface = retrofit.create(ApiInterface::class.java)
            }
            return apiINterface!!
        }
    }

    @GET("login")
    suspend fun logIn(
        @Query("username") username: String,
        @Query("username") password: String
    ): ApiResponse<LoginResponse>

    @PUT("users/{userObjectId}")
    suspend fun updateUser(
        @Path("userObjectId") userObjectId: String,
        @Query("timezone") timezone: String,
        @Header("X-Parse-Session-Token") sessionToken: String
    ): ApiResponse<UpdateUserResponse>
}