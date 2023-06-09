package com.example.capstone.response


import com.google.gson.annotations.SerializedName

data class LoginResult(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)