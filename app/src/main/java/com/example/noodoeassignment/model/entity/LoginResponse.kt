package com.example.noodoeassignment.model.entity

data class LoginResponse(
    val objectId: String,
    val username: String,
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