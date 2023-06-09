package com.example.capstone.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentRegisterBinding
import com.example.capstone.response.RegisterResponse
import com.example.capstone.utils.Result
import com.example.capstone.utils.ViewModelFactory


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLogin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        }

        binding.btRegister.setOnClickListener { it ->
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val gender = binding.edRegisterGender.text.toString()
            val age = binding.edRegisterAge.text.toString().toIntOrNull()
            val height = binding.edRegisterHeight.text.toString().toDoubleOrNull()
            val weight = binding.edRegisterWeight.text.toString().toDoubleOrNull()

            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)

            if (isFieldsValid(name, email, password, gender, age, height, weight)) {
                showLoading(true)
                register(name, email, password, gender, age!!, height!!, weight!!)
            } else {
                Toast.makeText(requireContext(), "Isian belum valid", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isFieldsValid(
        name: String,
        email: String,
        password: String,
        gender: String,
        age: Int?,
        height: Double?,
        weight: Double?
    ): Boolean {
        return name.isNotBlank() && email.isNotBlank() && password.isNotBlank() &&
                gender.isNotBlank() && age != null && age > 0 &&
                height != null && height > 0 && weight != null && weight > 0
    }

    private fun handleRegisterResult(result: Result<RegisterResponse>) {
        when (result) {
            is Result.Loading -> {
                showLoading(true)
                binding.textView2.text = "Sedang Register..."
            }
            is Result.Success -> {
                showLoading(false)
                processRegister(result.data)
            }
            is Result.Error -> {
                showLoading(false)
                Toast.makeText(context, result.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun processRegister(data: RegisterResponse) {
        if (data.error) {
            Toast.makeText(requireContext(), "Register Gagal", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Register Berhasil", Toast.LENGTH_LONG).show()
            val action =
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(isFromRegister = true)
            findNavController().navigate(action)
        }
    }

    private fun register(
        name: String,
        email: String,
        password: String,
        gender: String,
        age: Int,
        height: Double,
        weight: Double
    ) {
        registerViewModel.register(name, email, password, gender, age, height, weight)
            .observe(viewLifecycleOwner) { result ->
                result?.let { handleRegisterResult(it) }
            }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            progressBar.isVisible = state
            edRegisterEmail.isVisible = !state
            edRegisterName.isVisible = !state
            edRegisterPassword.isVisible = !state
            tvLogin.isVisible = !state
            btRegister.isVisible = !state
            edRegisterGender.isVisible = !state
            edRegisterAge.isVisible = !state
            edRegisterHeight.isVisible = !state
            edRegisterWeight.isVisible = !state

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}