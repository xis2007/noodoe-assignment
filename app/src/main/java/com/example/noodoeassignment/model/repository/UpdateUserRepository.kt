package com.example.noodoeassignment.model.repository

import com.example.noodoeassignment.api.ApiInterface
import com.example.noodoeassignment.api.Resource
import com.example.noodoeassignment.base.BaseRepository
import com.example.noodoeassignment.model.entity.LoginResponse
import com.example.noodoeassignment.model.entity.UpdateUserResponse
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

class UpdateUserRepository(
    private val apiInterface: ApiInterface
) : BaseRepository() {
    suspend fun updateUser(
        userObjectId: String,
        timezone: String,
        sessionToken: String
    ): Resource<UpdateUserResponse> {
        return remoteCall { apiInterface.updateUser(userObjectId, timezone, sessionToken) }
    }
}