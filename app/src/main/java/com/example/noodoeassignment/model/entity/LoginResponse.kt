package com.example.noodoeassignment.model.entity

data class LoginResponse(
    val username: String,
    val phone: String,
    val createdAt: String,
    val updatedAt: String,
    val objectId: String,
    val sessionToken: String
)