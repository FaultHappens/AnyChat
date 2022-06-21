package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.FragmentLoginBinding
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.presentation.vm.LoginFragmentVM
import okio.utf8Size
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    private val vm: LoginFragmentVM by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val tokenPreference = context?.getSharedPreferences("token", Context.MODE_PRIVATE)


        val rememberMe = tokenPreference?.getBoolean("rememberMe", false)
        if (rememberMe == true) {
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

            findNavController().navigate(R.id.profileFragment)

        }

        binding.forgetPasswordBtn.setOnClickListener {

            findNavController().navigate(R.id.passwordResetFragment)
        }

        binding.loginButton.setOnClickListener {
                val loginParam = LoginParam(
                    binding.usernameET.text.toString(),
                    binding.passwordET.text.toString(),
                    binding.rememberMeRadioBttn.isChecked
                )

                context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.edit()?.putBoolean(
                    "rememberMe", binding.rememberMeRadioBttn.isChecked
                )?.apply()

                vm.userLogin(loginParam)

        }
        binding.registerNowBttn.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }



    }
}