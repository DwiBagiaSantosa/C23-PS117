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
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentLoginBinding
import com.example.capstone.response.LoginResponse
import com.example.capstone.response.LoginResult
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
        savedInstanceState: Bundle?
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

            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)

            showLoading(true)

            loginViewModel.login(email, password).observe(viewLifecycleOwner) { result ->
                result?.let { handleLoginResult(it) }
            }
        }

        val isFromRegister: Boolean? = arguments?.getBoolean("is_from_register")
        if (isFromRegister == true) {
            onBackPressed()
        }
    }

    private fun handleLoginResult(result: Result<LoginResult>) {
        when (result) {
            is Result.Loading -> {
                showLoading(true)
                binding.textView6.text = "Sedang Login..."
            }
            is Result.Success -> {
                processLogin(result.data)
                showLoading(false)
            }
            is Result.Error -> {
                showLoading(false)
                Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun processLogin(data: LoginResult) {

        val userId = data.userId
        val name = data.name
        val gender = data.gender
        val age = data.age
        val height = data.height
        val weight = data.weight
        val bmr = data.bmr
        val token = data.token

        Preference.saveToken(token, requireContext())
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        requireActivity().finish()
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