package com.example.capstone.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentLoginBinding
import com.example.capstone.response.LoginResponse
import com.example.capstone.response.User
import com.example.capstone.utils.Preference
import com.example.capstone.utils.Result
import com.example.capstone.utils.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRegister.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.registerFragment)
        }

        binding.btLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isNotEmpty()) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)

                showLoading(true)

                loginViewModel.login(email, password).observe(viewLifecycleOwner) { result ->
                    result?.let { handleLoginResult(it) }
                }
            } else {
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        val isFromRegister: Boolean? = arguments?.getBoolean("is_from_register")
        if (isFromRegister == true) {
            onBackPressed()
        }
    }


    private fun handleLoginResult(result: Result<LoginResponse>) {
        when (result) {
            is Result.Loading -> {
                showLoading(true)
                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()

            }
            is Result.Success -> {
                val loginResponse = result.data
                val loggedInUser = User(
                    name = loginResponse.loginResult.name,
                    email = loginResponse.loginResult.email,
                    age = loginResponse.loginResult.age,
                    gender = loginResponse.loginResult.gender,
                    bmr = loginResponse.loginResult.bmr,
                    basictarget = loginResponse.loginResult.basictarget,
                    calories = loginResponse.loginResult.calories.toDouble(),
                    height = loginResponse.loginResult.height.toDouble(),
                    weight = loginResponse.loginResult.weight.toDouble(),
                    id = loginResponse.loginResult.userId,
                    token = loginResponse.loginResult.token
                )
                processLogin(loginResponse, loggedInUser)

                showLoading(false)
            }
            is Result.Error -> {
                showLoading(false)
                Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun processLogin(loginResponse: LoginResponse, loggedInUser: User) {
        if (loginResponse.error) {
            Toast.makeText(requireContext(), loginResponse.message, Toast.LENGTH_LONG).show()
        } else {

            loginViewModel.setLoggedInUser(loggedInUser)


            Preference.saveToken(loginResponse.loginResult.token, requireContext())


            val sharedPref = Preference.initPref(requireContext(), "onSignIn")
            sharedPref.edit().apply {
                putString("id", loggedInUser.id)
                putString("name", loggedInUser.name)
                putInt("age", loggedInUser.age)
                putString("gender", loggedInUser.gender)
                putFloat("basictarget", loggedInUser.basictarget.toFloat())
                putFloat("bmr", loggedInUser.bmr.toFloat())
                putFloat("calories", loggedInUser.calories.toFloat())
                putFloat("height", loggedInUser.height.toFloat())
                putFloat("weight", loggedInUser.weight.toFloat())
                apply()
            }

            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            requireActivity().finish()
        }
    }


    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            progressBar.isVisible = state
            textView6.isVisible = !state
            edLoginEmail.isVisible = !state
            edLoginPassword.isVisible = !state
            btLogin.isVisible = !state
            tvRegister.isVisible = !state
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}