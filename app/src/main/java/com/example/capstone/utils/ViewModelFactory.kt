package com.example.capstone.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.auth.LoginViewModel
import com.example.capstone.auth.RegisterViewModel
import com.example.capstone.network.ApiConfig
import com.example.capstone.network.AuthRepository



class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(createAuthRepository()) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(createAuthRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

    private fun createAuthRepository(): AuthRepository {
        val apiService = ApiConfig.getApiService(context)
        return AuthRepository(apiService)
    }
}
