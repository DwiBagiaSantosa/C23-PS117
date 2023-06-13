package com.example.capstone.response

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("bmr")
    val bmr: Double,
    @SerializedName("token")
    val token: String,
)
