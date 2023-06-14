package com.example.capstone.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.capstone.response.LoginResponse
import com.example.capstone.response.RegisterResponse
import com.example.capstone.utils.Result


class AuthRepository(private val apiService: ApiService) {


    fun postRegister(
        name: String,
        email: String,
        password: String,
        gender: String,
        age: Int,
        height: Double,
        weight: Double
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(name, email, password, gender, age, height, weight)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("RegisterViewModel", "postRegister: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }


    fun postLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "postLogin: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun updateBMR(userId: String, bmr: Double): Result<Unit> {
        return try {
            apiService.updateBMR(userId, bmr)
            Result.Success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "updateBMR: ${e.message.toString()}")
            Result.Error(e.message.toString())
        }
    }





}