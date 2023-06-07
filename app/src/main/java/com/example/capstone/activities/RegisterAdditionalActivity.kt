package com.example.capstone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.capstone.R
import com.example.capstone.database.AppDatabase
import com.example.capstone.helper.Preferences
import com.example.capstone.models.User
import com.example.capstone.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterAdditionalActivity : AppCompatActivity() {
    private lateinit var preferences: Preferences
    private lateinit var userRepository: UserRepository

    private lateinit var spinnerGender: Spinner
    private lateinit var edRegisterAge: EditText
    private lateinit var edRegisterHeight: EditText
    private lateinit var edRegisterWeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_additional)

        preferences = Preferences(this)
        val userDao = AppDatabase.getDatabase(this).userDao()
        userRepository = UserRepository(userDao)

        spinnerGender = findViewById(R.id.spinner_gender)
        edRegisterAge = findViewById(R.id.ed_register_age)
        edRegisterHeight = findViewById(R.id.ed_register_height)
        edRegisterWeight = findViewById(R.id.ed_register_weight)

        val btnRegisterAdditional = findViewById<Button>(R.id.btn_register_additional)


        val genderOptions = arrayOf("Gender ?", "L", "P")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = genderAdapter

        btnRegisterAdditional.setOnClickListener {
            val gender = spinnerGender.selectedItem.toString()
            val age = edRegisterAge.text.toString().toIntOrNull()
            val height = edRegisterHeight.text.toString().toDoubleOrNull()
            val weight = edRegisterWeight.text.toString().toDoubleOrNull()

            if (validateInputs(gender, age, height, weight)) {
                val tempUser = preferences.tempUser
                if (tempUser != null) {
                    val user = tempUser.copy(
                        gender = gender,
                        age = age ?: 0,
                        height = height ?: 0.0,
                        weight = weight ?: 0.0
                    )
                    registerUser(user)
                } else {
                    Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInputs(gender: String, age: Int?, height: Double?, weight: Double?): Boolean {
        if (gender == "Pilih Gender" || age == null || height == null || weight == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            userRepository.addUser(user)
        }
        preferences.session = true
        preferences.token = "your_token_here"
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}