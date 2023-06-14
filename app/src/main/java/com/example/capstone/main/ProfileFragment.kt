package com.example.capstone.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.capstone.auth.LoginViewModel
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.response.User
import com.example.capstone.utils.Preference
import com.example.capstone.utils.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvName: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvBMR: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false,)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tvName = binding.tvName
        tvGender = binding.tvGender
        tvAge = binding.tvAge
        tvHeight = binding.tvHeight
        tvWeight = binding.tvWeight
        tvBMR = binding.tvBMR


        val loggedInUser = Preference.getLoggedInUser(requireContext())

        tvName.text = loggedInUser.name
        tvGender.text = loggedInUser.gender
        tvAge.text = loggedInUser.age.toString()
        tvHeight.text = loggedInUser.height.toString()
        tvWeight.text = loggedInUser.weight.toString()
        tvBMR.text = loggedInUser.bmr.toString()

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}