package com.example.anychat.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            //TODO: send request to login
        }
        binding.registerNowBttn.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
        binding.forgotPasswordBttn.setOnClickListener {

        }


    }
}