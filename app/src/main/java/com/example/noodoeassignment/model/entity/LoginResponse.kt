package com.example.noodoeassignment.model.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
//    @SerializedName("objectId")
    val objectId: String,
//    @SerializedName("username")
    val username: String,
//    @SerializedName("code")
    val code: String,
    val isVerifiedReportEmail: String,
    val reportEmail: String,
    val createdAt: String,
    val updatedAt: String,
    val timezone: Int,
    val parameter: Int,
    val number: Int,
    val phone: String,
    val sessionToken: String
)