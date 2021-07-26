package com.example.noodoeassignment.api

import com.example.noodoeassignment.AppConstant
import com.example.noodoeassignment.BuildConfig
import com.example.noodoeassignment.model.entity.LoginResponse
import com.example.noodoeassignment.model.entity.UpdateUserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    companion object {
        var apiINterface: ApiInterface? = null
        fun getInstance() : ApiInterface {

            if (apiINterface == null) {

                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val interceptor = object : Interceptor{
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        var request = chain.request()
                        request = request.newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("X-Parse-Application-Id", AppConstant.applicationId)
                            .build()
                        return chain.proceed(request)
                    }
                }

                val client = OkHttpClient
                    .Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(interceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                apiINterface = retrofit.create(ApiInterface::class.java)
            }
            return apiINterface!!
        }
    }

    @GET("login")
    suspend fun logIn(
        @Query("username") username: String,
        @Query("password") password: String
    ): retrofit2.Response<LoginResponse>

    @PUT("users/{userObjectId}")
    suspend fun updateUser(
        @Path("userObjectId") userObjectId: String,
        @Query("timezone") timezone: String,
        @Header("X-Parse-Session-Token") sessionToken: String
    ): retrofit2.Response<UpdateUserResponse>
}