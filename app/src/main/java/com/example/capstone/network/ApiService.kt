package com.example.capstone.network


import com.example.capstone.response.*
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


    @FormUrlEncoded
    @POST("update-bmr")
    suspend fun updateBMR(
        @Field("userId") userId: Int,
        @Field("bmr") bmr: Double,
        @Field("calories") calories: Double

    ): UpdateBMRResponse



}
