package com.example.capstone.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.hide()
    }
}