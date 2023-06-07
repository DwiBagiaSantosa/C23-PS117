package com.example.capstone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.capstone.R


import com.example.capstone.helper.Preferences

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = Preferences(this)

        val btnClassification = findViewById<Button>(R.id.bt_classif)

        btnClassification.setOnClickListener {
            navigateToClassif()
        }

        checkSession()
    }

    private fun navigateToClassif() {
        val intent = Intent(this, ClassifActivity::class.java)
        startActivity(intent)
        finish()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_logout ->  {
                logoutUser()
                startActivity(intent)
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}



