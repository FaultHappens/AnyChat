package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
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

        val inputsValidMap = mutableMapOf<EditText, Boolean>()


        vm.userExistLiveData.observe(viewLifecycleOwner) {
            if(it){
                binding.usernameET.error = "Username already exist"
                inputsValidMap[binding.usernameET] = false
            }
        }
        vm.emailExistLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.emailET.error = "Email already exist"
                inputsValidMap[binding.emailET] = false
            }
        }


        binding.emailET.addTextChangedListener {

            if (binding.emailET.text.length < 3 || !android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailET.text.toString()).matches()) {
                binding.emailET.error = "Please provide valid email!"
               inputsValidMap[binding.emailET] = false
            }

            else{
                binding.passwordRepeatET.error = null
                vm.emailExist(binding.emailET.text.toString())
                inputsValidMap[binding.emailET] = true
            }
        }
        binding.usernameET.addTextChangedListener {
            if (binding.usernameET.text.toString().length < 3) {
                binding.usernameET.error = "Please provide valid username!"
                inputsValidMap[binding.usernameET] = false
            }
            else{
                binding.passwordRepeatET.error = null
                vm.userExist(binding.usernameET.text.toString())
                inputsValidMap[binding.usernameET] = true
            }
        }
        binding.passwordET.addTextChangedListener {
            if (binding.passwordET.text.toString().length < 8) {
                binding.passwordET.error = "password too short!"
                inputsValidMap[binding.passwordET] = false
            }
            else if (binding.passwordET.text.toString().length > 20) {
                binding.passwordET.error = "password too long!"
                inputsValidMap[binding.passwordET] = false
            }
            else{
                binding.passwordRepeatET.error = null
                inputsValidMap[binding.passwordET] = true
            }
        }
        binding.passwordRepeatET.addTextChangedListener {
            if (binding.passwordRepeatET.text.toString() == binding.passwordET.text.toString()) {
                binding.passwordRepeatET.error = null
                inputsValidMap[binding.passwordRepeatET] = false
            } else {
                binding.passwordRepeatET.error = "Passwords do not match!"
                inputsValidMap[binding.passwordRepeatET] = true
            }
        }




        binding.registerBttn.setOnClickListener {
            if (inputsValidMap.values.all { it }
                && inputsValidMap.keys.all { it.text.toString().isNotBlank() }
            ) {
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



    }
}