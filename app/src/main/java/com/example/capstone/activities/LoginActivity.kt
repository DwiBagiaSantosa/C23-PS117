package com.example.capstone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.capstone.R
import com.example.capstone.database.AppDatabase
import com.example.capstone.helper.Preferences
import com.example.capstone.repositories.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var preferences: Preferences
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferences = Preferences(this)
        val userDao = AppDatabase.getDatabase(this).userDao()
        userRepository = UserRepository(userDao)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val edLoginEmail = findViewById<EditText>(R.id.ed_login_email)
        val edLoginPassword = findViewById<EditText>(R.id.ed_login_password)

        val txtSignUp = findViewById<TextView>(R.id.txt_sign_up)

        btnLogin.setOnClickListener {
            val email = edLoginEmail.text.toString()
            val password = edLoginPassword.text.toString()
            loginUser(email, password)
        }

        txtSignUp.setOnClickListener {
            navigateToRegister()
        }


        checkSession()
    }

    private fun checkSession() {
        if (preferences.session) {
            navigateToMain()
        }
    }

    private fun loginUser(email: String, password: String) {
        GlobalScope.launch {
            val user = userRepository.getUser(email, password)
            if (user != null) {
                preferences.session = true
                preferences.token = "your_token_here"
                navigateToMain()
            } else {
                runOnUiThread {
                    showToast("Invalid email or password")
                }
            }
        }
    }


    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}