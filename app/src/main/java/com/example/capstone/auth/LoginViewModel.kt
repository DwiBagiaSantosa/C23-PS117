package com.example.capstone.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.capstone.network.AuthRepository
import com.example.capstone.response.LoginResult
import com.example.capstone.response.User
import com.example.capstone.utils.Result

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loggedInUser = MutableLiveData<User>()
    val loggedInUser: LiveData<User> = _loggedInUser

    fun setLoggedInUser(user: User) {
        _loggedInUser.value = user
    }

    fun login(email: String, password: String) = authRepository.postLogin(email, password)


}