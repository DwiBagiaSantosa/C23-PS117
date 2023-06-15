package com.example.capstone.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.capstone.response.User

object Preference {
    fun initPref(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun editorPreference(context: Context, name: String): SharedPreferences.Editor {
        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }


    fun saveToken(token: String, context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.putString("token", token)
        editor.apply()
    }

    fun logOut(context: Context) {
        val editor = editorPreference(context, "onSignIn")
        editor.remove("token")
        editor.remove("status")
        editor.apply()
    }

    private const val PREF_NAME = "onSignIn"

    fun getLoggedInUser(context: Context): User {
        val sharedPref = initPref(context, PREF_NAME)

        return User(
            id = sharedPref.getString("id", "")!!,
            name = sharedPref.getString("name", "")!!,
            email = sharedPref.getString("email", "")!!,
            age = sharedPref.getInt("age", 0),
            gender = sharedPref.getString("gender", "")!!,
            bmr = sharedPref.getFloat("bmr", 0f).toDouble(),

            calories = sharedPref.getFloat("calories", 0f).toDouble(),
            height = sharedPref.getFloat("height", 0f).toDouble(),
            weight = sharedPref.getFloat("weight", 0f).toDouble(),
            token = sharedPref.getString("token", "")!!
        )
    }

    fun updateBMR(bmr: Double, context: Context, calories: Double) {
        val editor = editorPreference(context, PREF_NAME)
        editor.putFloat("bmr", bmr.toFloat())
        editor.putFloat("calories", calories.toFloat())
        editor.apply()
    }


}