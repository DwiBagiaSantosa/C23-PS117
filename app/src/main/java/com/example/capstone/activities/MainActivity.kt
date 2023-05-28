package com.example.capstone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.capstone.R
import com.example.capstone.helper.Preferences

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = Preferences(this)

        val btnLogout = findViewById<Button>(R.id.btn_logout)

        btnLogout.setOnClickListener {
            logoutUser()
        }

        checkSession()
    }

    private fun checkSession() {
        if (!preferences.session) {
            navigateToLogin()
        }
    }

    private fun logoutUser() {
        preferences.clearData()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
