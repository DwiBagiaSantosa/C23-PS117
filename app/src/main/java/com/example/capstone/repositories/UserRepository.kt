package com.example.capstone.repositories

import com.example.capstone.models.User

class UserRepository(private val userDao: UserDao) {
    suspend fun getUser(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}
