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
import java.lang.StringBuilder


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
            val strBuilder = StringBuilder()
            if(it.firstname != null) {
                strBuilder.append(it.firstname)
                strBuilder.append(" ")
            }
            if(it.lastname != null){
                strBuilder.append(it.lastname)
            }
            if(strBuilder.isNotEmpty()){
                binding.fullnameET.text = strBuilder.toString()
            }

            binding.descriptionET.text = it.about ?: "No Description"

        }
        val username = context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.getString("username", null)


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

        if(username == null)
            findNavController().navigate(R.id.loginFragment)
        else
           vm.getUser(username)

    }
}