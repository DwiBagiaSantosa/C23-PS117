package com.example.capstone.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.capstone.network.AuthRepository
import com.example.capstone.utils.Result

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun login(email: String, password: String) = authRepository.postLogin(email, password)
        .map { result ->
            when (result) {
                is Result.Success -> {
                    Result.Success(result.data.loginResult)
                }
                is Result.Loading -> Result.Loading
                is Result.Error -> Result.Error(result.error)
            }
        }
}