package com.example.anychat.presentation.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.FragmentProfileEditBinding
import com.example.anychat.domain.model.param.UserUpdateParam
import com.example.anychat.presentation.vm.ProfileEditFragmentVM
import com.example.anychat.presentation.vm.ProfileFragmentVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.StringBuilder


class ProfileEditFragment : Fragment() {


    lateinit var binding: FragmentProfileEditBinding
    private val vm: ProfileEditFragmentVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username =
            context?.getSharedPreferences("token", Context.MODE_PRIVATE)?.getString("username", null)
                ?: return
        vm.getUser(username)

        vm.userDTOLiveData.observe(viewLifecycleOwner) {
            binding.firstnameET.setText(it.firstname)
            binding.lastnameET.setText(it.lastname)
            binding.descriptionET.setText(it.about)

        }



        binding.saveBtn.setOnClickListener {
            val userUpdateParam = UserUpdateParam(
                binding.firstnameET.text.toString(),
                binding.lastnameET.text.toString(),
                binding.descriptionET.text.toString()
            )
            vm.updateUser(username, userUpdateParam)
            findNavController().navigate(R.id.profileFragment)
        }
    }
}