package com.example.noodoeassignment.model.repository

import com.example.noodoeassignment.api.ApiInterface
import com.example.noodoeassignment.api.Resource
import com.example.noodoeassignment.base.BaseRepository
import com.example.noodoeassignment.model.entity.LoginResponse

class LoginRepository(
    private val apiInterface: ApiInterface
) : BaseRepository() {
    suspend fun login(email: String, pw: String): Resource<LoginResponse> {
        return remoteCall { apiInterface.logIn(email, pw) }
    }
}