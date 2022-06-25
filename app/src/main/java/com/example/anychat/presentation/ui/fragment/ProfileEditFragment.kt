package com.example.anychat.presentation.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.anychat.databinding.FragmentProfileEditBinding
import com.example.anychat.domain.model.param.UserUpdateParam


class ProfileEditFragment : Fragment() {


    lateinit var binding: FragmentProfileEditBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveBtn.setOnClickListener {
            val userUpdateParam = UserUpdateParam(
                binding.firstnameET.text.toString(),
                binding.lastnameET.text.toString(),
                binding.descriptionET.text.toString()
            )

        }
    }
}