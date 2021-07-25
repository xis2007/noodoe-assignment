package com.example.noodoeassignment.api

data class ApiResponse<T>(
    val errorCode: Int,
    val errorMessage: String,
    val data: T
)
