package com.example.capstone.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.auth.LoginViewModel
import com.example.capstone.network.ApiConfig
import com.example.capstone.network.AuthRepository
import com.example.capstone.response.LoginResult
import com.example.capstone.utils.Result
import com.example.capstone.utils.ViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var classificationButton: Button

    private lateinit var edBasicTarget : TextView
    private lateinit var yourBMRText: TextView
    private lateinit var tvName: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvBMR: TextView




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        edBasicTarget = view.findViewById(R.id.ed_basic_target)
        tvName = view.findViewById(R.id.tvName)
        tvGender = view.findViewById(R.id.tvGender)
        tvAge = view.findViewById(R.id.tvAge)
        tvHeight = view.findViewById(R.id.tvHeight)
        tvWeight = view.findViewById(R.id.tvWeight)
        tvBMR = view.findViewById(R.id.tvBMR)
        yourBMRText = view.findViewById(R.id.your_bmr_text)



        classificationButton = view.findViewById(R.id.bt_classif)
        classificationButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_classificationActivity)
        }



        val authRepository = AuthRepository(ApiConfig.getApiService(requireContext()))

        lifecycleScope.launch {
            val result = authRepository.getUsers()

            when (result) {
                is Result.Success -> {
                    val users = result.data.users
                    if (users.isNotEmpty()) {
                        val user = users[0]
                        val bmr = user.bmr
                        val names = user.name
                        val genders = user.gender
                        val ages = user.age.toString()
                        val heights = user.height.toString()
                        val weights = user.weight.toString()

                        tvName.text = "$names"
                        tvGender.text = "$genders"
                        tvAge.text = "$ages"
                        tvHeight.text = "$heights"
                        tvWeight.text = "$weights"
                        tvBMR.text = "$bmr"
                        yourBMRText.text = "$bmr"
                        edBasicTarget.text = "Basic Target \n" + "$bmr"
                    } else {
                        yourBMRText.text = "No users found"
                        edBasicTarget.text = "No users found"
                    }
                }
                is Result.Error -> {
                    Toast.makeText(activity, "Failed to get BMR value", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    // Show loading indicator if needed
                }
            }
        }

        //...

        return view

    }

    //...
}
