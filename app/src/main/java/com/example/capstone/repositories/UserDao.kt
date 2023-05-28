package com.example.capstone.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.capstone.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String): User?

    @Insert
    fun addUser(user: User)
}
