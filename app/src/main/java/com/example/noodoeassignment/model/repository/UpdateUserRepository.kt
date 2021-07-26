package com.example.noodoeassignment.model.repository

import com.example.noodoeassignment.api.ApiInterface
import com.example.noodoeassignment.api.Resource
import com.example.noodoeassignment.base.BaseRepository
import com.example.noodoeassignment.model.entity.UpdateUserResponse

class UpdateUserRepository(
    private val apiInterface: ApiInterface
): BaseRepository() {
    suspend fun updateUser(
        userObjectId: String,
        timezone: String,
        sessionToken: String
    ): Resource<UpdateUserResponse> {
        return remoteCall { apiInterface.updateUser(userObjectId, timezone, sessionToken) }
    }
}