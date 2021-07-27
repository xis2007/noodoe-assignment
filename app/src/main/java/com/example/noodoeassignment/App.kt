package com.example.noodoeassignment

import android.app.Application
import com.example.noodoeassignment.api.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {
    companion object {
        val retrofitService: ApiInterface by lazy {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val interceptor = object : Interceptor {
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

            retrofit.create(ApiInterface::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}