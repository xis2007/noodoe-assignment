package com.example.noodoeassignment.api


data class Resource<out T>(val status: Status, val data: T?, val apiError: ApiError?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(apiError: ApiError, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, apiError)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}