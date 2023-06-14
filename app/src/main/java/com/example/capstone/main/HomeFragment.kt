package com.example.capstone.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.capstone.R
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.utils.Preference
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private lateinit var edBasicTarget: TextView
    private lateinit var yourBMRText: TextView
    private lateinit var classificationButton: FloatingActionButton
    private lateinit var profileButton: FloatingActionButton

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
        yourBMRText = binding.yourBmrText

        profileButton = binding.btProfile
        profileButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        classificationButton = binding.btClassif
        classificationButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_classificationFragment)
        }

        val loggedInUser = Preference.getLoggedInUser(requireContext())
        yourBMRText.text = loggedInUser.bmr.toString()
        edBasicTarget.text = "Basic Target \n" + loggedInUser.bmr.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
