package com.example.capstone.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.auth.LoginViewModel
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.response.User
import com.example.capstone.utils.Preference
import com.example.capstone.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var classificationButton: Button
    private lateinit var edBasicTarget: TextView
    private lateinit var yourBMRText: TextView
    private lateinit var tvName: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvBMR: TextView

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edBasicTarget = binding.edBasicTarget
        tvName = binding.tvName
        tvGender = binding.tvGender
        tvAge = binding.tvAge
        tvHeight = binding.tvHeight
        tvWeight = binding.tvWeight
        tvBMR = binding.tvBMR
        yourBMRText = binding.yourBmrText

        classificationButton = binding.btClassif
        classificationButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_classificationActivity)
        }

        val sharedPref = Preference.initPref(requireContext(), "onSignIn")
        val loggedInUser = User(
            id = sharedPref.getString("id", "")!!,
            name = sharedPref.getString("name", "")!!,
            email = sharedPref.getString("email", "")!!,
            age = sharedPref.getInt("age", 0),
            gender = sharedPref.getString("gender", "")!!,
            bmr = sharedPref.getFloat("bmr", 0f).toDouble(),
            height = sharedPref.getFloat("height", 0f).toDouble(),
            weight = sharedPref.getFloat("weight", 0f).toDouble(),
            token = sharedPref.getString("token", "")!!
        )

        tvName.text = loggedInUser.name
        tvGender.text = loggedInUser.gender
        tvAge.text = loggedInUser.age.toString()
        tvHeight.text = loggedInUser.height.toString()
        tvWeight.text = loggedInUser.weight.toString()
        tvBMR.text = loggedInUser.bmr.toString()
        yourBMRText.text = loggedInUser.bmr.toString()
        edBasicTarget.text = "Basic Target \n" + loggedInUser.bmr.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
