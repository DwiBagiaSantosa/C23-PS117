package com.example.capstone.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.capstone.network.AuthRepository
import com.example.capstone.response.LoginResult
import com.example.capstone.utils.Result

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun login(email: String, password: String) = authRepository.postLogin(email, password)
}