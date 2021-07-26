package com.example.noodoeassignment.base

import com.example.noodoeassignment.api.ApiError
import com.example.noodoeassignment.api.ApiErrorCodeMessage
import com.example.noodoeassignment.api.Resource
import retrofit2.Response

open class BaseRepository {
    suspend fun <T> remoteCall(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call.invoke()

            when (response.isSuccessful) {
                true -> { Resource.success(response.body()) }
                else -> {
                    Resource.error(ApiError(response.code(), response.message()), null)
                }
            }
        } catch (e: Exception) {
            Resource.error(ApiError(ApiErrorCodeMessage.SYSTEM_ERROR_CODE, e.localizedMessage), null)
        }
    }
}