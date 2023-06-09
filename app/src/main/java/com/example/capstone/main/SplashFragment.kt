package com.example.capstone.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.utils.Preference

class SplashFragment : Fragment() {

    companion object {
        private const val DURATION: Long = 1000
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = Preference.initPref(requireContext(), "onSignIn")
        val token = sharedPref.getString("token", "")

        val action = if (token != "") {
            SplashFragmentDirections.actionSplashFragmentToMainActivity()
        } else {
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        }

        handler.postDelayed({
            findNavController().navigate(action)
            if (token != "") {
                requireActivity().finish()
            }
        }, DURATION)
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }
}
