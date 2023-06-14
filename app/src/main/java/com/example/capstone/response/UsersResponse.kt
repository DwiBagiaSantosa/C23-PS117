package com.example.capstone.response

data class UsersResponse(
    val users: List<User>
    )

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val bmr: Double,
    val height: Double,
    val weight: Double,
    val token: String

) {
    constructor(id: String, name: String, age: Int, gender: String, bmr: Double, height: Double, weight: Double, password: String, token :String)
            : this(id, name,  age, gender, bmr, height, weight,token)
}
