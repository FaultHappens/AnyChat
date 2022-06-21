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
import com.example.anychat.domain.model.param.LoginParam
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


        vm.tokenDTOLiveData.observe(viewLifecycleOwner) {
            val preference = context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.edit()

            preference?.putString(
                "access_token", it.access_token
            )?.apply()

            preference?.putLong(
                "expires_in", it.expires_in
            )?.apply()

            preference?.putString(
                "refresh_token", it.refresh_token
            )?.apply()

            preference?.putLong(
                "refresh_token_expires_in", it.refresh_expires_in
            )?.apply()
            findNavController().navigate(R.id.profileFragment)
        }

        binding.registerBttn.setOnClickListener {
            var inputValid: Boolean = true
            if (binding.emailET.text.toString().length < 3 || !android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailET.text.toString()).matches()) {
                binding.emailET.error = "Please provide valid email!"
                inputValid = false
            }

            if (binding.usernameET.text.toString().length < 3) {
                binding.usernameET.error = "Please provide valid email!"
                inputValid = false
            }

            if (binding.passwordET.text.toString().length < 3) {
                binding.passwordET.error = "Please provide valid password!"
                inputValid = false
            }

            if(binding.passwordRepeatET.text.toString() != binding.passwordET.text.toString()){
                binding.passwordRepeatET.error = "Passwords do not match!"
                inputValid = false
            }

            if (inputValid) {
                val registrationParam = RegistrationParam(
                    binding.usernameET.text.toString(),
                    binding.emailET.text.toString(),
                    binding.passwordET.text.toString()
                )
                vm.userRegistration(registrationParam)
            }

        }

        binding.loginNowBttn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.passwordRepeatET.addTextChangedListener {
            if (binding.passwordRepeatET.text.toString() == binding.passwordET.text.toString()) {
                binding.passwordRepeatET.error = null
            } else {
                binding.passwordRepeatET.error = "Passwords do not match!"
            }
        }


    }
}