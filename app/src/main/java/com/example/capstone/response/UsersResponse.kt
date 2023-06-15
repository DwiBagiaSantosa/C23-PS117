package com.example.capstone.response

data class UsersResponse(
    val users: List<User>
    )

data class User(
    val id: String,
    val name: String,
    val email : String,
    val age: Int,
    val gender: String,
    val height: Double,
    val weight: Double,
    val basictarget: Double,
    val bmr: Double,
    val calories: Double,
    val token: String
) {
    constructor(
        id: String,
        name: String,
        email:String,
        age: Int,
        gender: String,
        height: Double,
        weight: Double,
        basictarget: Double,
        bmr: Double,
        calories: Double,
        password: String,
        token: String
    )
            :this(id, name, email, age, gender, bmr, basictarget, height, calories, weight, token)
}

