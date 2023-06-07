package com.example.capstone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.capstone.R
import com.example.capstone.database.AppDatabase
import com.example.capstone.helper.Preferences
import com.example.capstone.models.User
import com.example.capstone.repositories.UserRepository

class RegisterActivity : AppCompatActivity() {
    private lateinit var preferences: Preferences
    private lateinit var userRepository: UserRepository
    private lateinit var edRegisterPassword: EditText
    private lateinit var edRegisterConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        preferences = Preferences(this)
        val userDao = AppDatabase.getDatabase(this).userDao()
        userRepository = UserRepository(userDao)

        edRegisterPassword = findViewById(R.id.ed_register_password)
        edRegisterConfirmPassword = findViewById(R.id.ed_register_confirm_password)

        val btnNext = findViewById<Button>(R.id.btn_next)
        val edRegisterName = findViewById<EditText>(R.id.ed_register_name)
        val edRegisterEmail = findViewById<EditText>(R.id.ed_register_email)

        val txtSignIn = findViewById<TextView>(R.id.txt_sign_in)

        btnNext.setOnClickListener {
            val name = edRegisterName.text.toString()
            val email = edRegisterEmail.text.toString()
            val password = edRegisterPassword.text.toString()
            val confirmPassword = edRegisterConfirmPassword.text.toString()
            if (validateInputs(name, email, password, confirmPassword)) {
                val user = User(name = name, email = email, password = password)
                preferences.tempUser = user
                navigateToRegisterAdditional()
            }
        }

        txtSignIn.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun validateInputs(name: String, email: String, password: String, confirmPassword: String): Boolean {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        } else if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun navigateToRegisterAdditional() {
        val intent = Intent(this, RegisterAdditionalActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }



}