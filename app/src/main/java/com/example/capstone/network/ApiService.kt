package com.example.capstone.network


import com.example.capstone.response.LoginResponse
import com.example.capstone.response.RegisterResponse
import com.example.capstone.response.UsersResponse
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("height") height: Double,
        @Field("weight") weight: Double
    ): RegisterResponse


    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

}
