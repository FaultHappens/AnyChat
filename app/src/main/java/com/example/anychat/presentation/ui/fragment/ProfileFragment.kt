package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.auth0.android.jwt.JWT
import com.example.anychat.R
import com.example.anychat.databinding.FragmentLoginBinding
import com.example.anychat.databinding.FragmentProfileBinding
import com.example.anychat.databinding.FragmentRegistrationBinding
import com.example.anychat.presentation.vm.ProfileFragmentVM
import com.example.anychat.presentation.vm.RegistrationFragmentVM
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {


    lateinit var binding: FragmentProfileBinding
    private val vm: ProfileFragmentVM by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.userDTOLiveData.observe(viewLifecycleOwner) {
            binding.nameET.text = it.username
            binding.descriptionET.text = it.about ?: "No Description"

        }
        val tokenPreference = context?.getSharedPreferences("token", Context.MODE_PRIVATE)
        val authToken = tokenPreference?.getString("access_token", null)
        if(authToken == null){
            findNavController().navigate(R.id.loginFragment)
            return
        }
        val username = JWT(authToken).claims["given_name"]?.asString()!!

        binding.editBttn.setOnClickListener {
            findNavController().navigate(R.id.profileEditFragment)
        }
        binding.chatBtn.setOnClickListener {
            findNavController().navigate(R.id.chatFragment)
        }
        binding.logoutBtn.setOnClickListener {
            context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.edit()?.clear()?.apply()
            findNavController().navigate(R.id.loginFragment)
        }


        vm.getUser(username)

    }
}