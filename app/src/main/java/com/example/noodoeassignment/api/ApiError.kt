package com.example.noodoeassignment.api

data class ApiError(val errorCode : Int?, val errorMessage: String?)

object ApiErrorCodeMessage {
    const val SYSTEM_ERROR_CODE = -1
}