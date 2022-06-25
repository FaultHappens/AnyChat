package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.auth0.android.jwt.JWT
import com.example.anychat.R
import com.example.anychat.databinding.FragmentLoginBinding
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.presentation.vm.LoginFragmentVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.util.*

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


            val username = JWT(it.access_token).claims["given_name"]?.asString()!!

            prefference?.putString(
                "username", username
            )?.apply()

            prefference?.putString(
                "access_token", it.access_token
            )?.apply()

            prefference?.putLong(
                "expires_in",  LocalDateTime.now().second + it.expires_in
            )?.apply()

            prefference?.putString(
                "refresh_token", it.refresh_token
            )?.apply()

            findNavController().navigate(R.id.profileFragment)
        }
        vm.wrongCredentialLiveData.observe(viewLifecycleOwner){
            binding.usernameET.error = "Wrong username or password"
            binding.passwordET.error = "Wrong username or password"
        }

        binding.forgetPasswordBtn.setOnClickListener {

            findNavController().navigate(R.id.passwordResetFragment)
        }

        binding.loginButton.setOnClickListener {
                val loginParam = LoginParam(
                    binding.usernameET.text.toString(),
                    binding.passwordET.text.toString(),
                    binding.rememberMeCheckBttn.isChecked
                )

                context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.edit()?.putBoolean(
                    "rememberMe", binding.rememberMeCheckBttn.isChecked
                )?.apply()

                vm.userLogin(loginParam)

        }
        binding.registerNowBttn.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
    }
}

