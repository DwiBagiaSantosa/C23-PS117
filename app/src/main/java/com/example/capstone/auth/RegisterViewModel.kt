package com.example.capstone.auth

import androidx.lifecycle.ViewModel
import com.example.capstone.network.AuthRepository

class RegisterViewModel (private val authRepository: AuthRepository) : ViewModel() {
    fun register(name: String, email: String, password: String, gender: String, age: Int, height: Double, weight: Double) =
        authRepository.postRegister(name, email, password, gender, age, height, weight)


}