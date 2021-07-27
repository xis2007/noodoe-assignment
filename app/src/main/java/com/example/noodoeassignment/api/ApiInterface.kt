package com.example.noodoeassignment.api


import com.example.noodoeassignment.model.entity.LoginResponse
import com.example.noodoeassignment.model.entity.UpdateUserResponse
import retrofit2.http.*

interface ApiInterface {
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