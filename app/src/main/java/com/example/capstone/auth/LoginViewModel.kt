package com.example.capstone.auth

import androidx.lifecycle.ViewModel
import com.example.capstone.network.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun login(email: String, password: String) = authRepository.postLogin(email, password)
}