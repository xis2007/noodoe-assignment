package com.example.noodoeassignment.api

import com.example.noodoeassignment.model.entity.LoginResponse
import com.example.noodoeassignment.model.entity.UpdateUserResponse
import com.example.noodoeassignment.model.repository.UpdateUserRepository
import retrofit2.http.*

interface ApiInterface {
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