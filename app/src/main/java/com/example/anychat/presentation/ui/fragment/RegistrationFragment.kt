package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.FragmentLoginBinding
import com.example.anychat.databinding.FragmentRegistrationBinding
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.presentation.vm.RegistrationFragmentVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    lateinit var binding: FragmentRegistrationBinding

    private val vm: RegistrationFragmentVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authToken = context?.getSharedPreferences("token", Context.MODE_PRIVATE)
            ?.getString("access_token", null)

        if (authToken != null) {
            Log.d("token", authToken)
            findNavController().navigate(R.id.profileFragment)
        }
        vm.tokenDTOLiveData.observe(viewLifecycleOwner) {
            val prefference = context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.edit()

            prefference?.putString(
                "access_token", it.access_token
            )?.apply()

            prefference?.putLong(
                "expires_in", it.expires_in
            )?.apply()

            prefference?.putString(
                "refresh_token", it.refresh_token
            )?.apply()

            prefference?.putLong(
                "refresh_token_expires_in", it.refresh_expires_in
            )?.apply()
        }

        binding.registerBttn.setOnClickListener {

        }

        binding.loginNowBttn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.passwordRepeatET.addTextChangedListener {
            if (binding.passwordRepeatET.text == binding.passwordET.text) {
                //TODO: green
            } else {
                //TODO: red
            }
        }

        binding.registerBttn.setOnClickListener {


            val registrationParam = RegistrationParam(
                binding.usernameET.text.toString(),
                binding.emailET.text.toString(),
                binding.passwordET.text.toString()

            )
            vm.userRegistration(registrationParam)

        }

    }
}