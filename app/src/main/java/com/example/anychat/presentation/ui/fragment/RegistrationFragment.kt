package com.example.anychat.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.FragmentLoginBinding
import com.example.anychat.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    lateinit var binding : FragmentRegistrationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerBttn.setOnClickListener {

        }

        binding.loginNowBttn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.passwordRepeatET.addTextChangedListener {
            if(binding.passwordRepeatET.text == binding.passwordET.text){
                //TODO: green
            }else{
                //TODO: red
            }
        }
    }
}