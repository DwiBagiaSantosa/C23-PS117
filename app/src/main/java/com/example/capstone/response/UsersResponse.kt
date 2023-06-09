package com.example.capstone.response

data class UsersResponse(val users: List<User>)

data class User(
    val name: String,
    val email: String,
    val password: String,
    val gender: String,
    val age: Int,
    val height: Double,
    val weight: Double,
    val bmr: Double
    )
