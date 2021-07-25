package com.example.noodoeassignment.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.noodoeassignment.api.*

open class BaseRepository {
    suspend fun <T> remoteCall(call: suspend () -> ApiResponse<T>): Resource<T> {
        return try {
            val apiResponse = call.invoke()

            when (apiResponse.errorCode) {
                0 -> { Resource.success(apiResponse.data) }
                else -> { Resource.error(ApiError(apiResponse.errorCode, apiResponse.errorMessage), null) }
            }
        } catch (e: Exception) {
            Resource.error(ApiError(ApiErrorCodeMessage.SYSTEM_ERROR_CODE, e.localizedMessage), null)
        }
    }
}