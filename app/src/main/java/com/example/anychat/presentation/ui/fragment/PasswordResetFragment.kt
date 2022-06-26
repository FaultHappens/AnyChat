package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anychat.R
import com.example.anychat.databinding.FragmentPasswordResetBinding
import com.example.anychat.domain.model.param.ResetPasswordParam
import com.example.anychat.presentation.vm.PasswordResetFragmentVM
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.math.abs


class PasswordResetFragment : Fragment() {

    lateinit var binding: FragmentPasswordResetBinding

    private val passwordResetWaitTime = 180000L

    private val vm: PasswordResetFragmentVM by viewModel()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordResetBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.getSharedPreferences("password-reset", Context.MODE_PRIVATE)?.getLong(
            "timer",
            0
        )?.let {
            val diff = it - Date().time
            if(diff > 0 ) {
              addTimer(diff)
            }
        }
        val inputsValidMap = mutableMapOf<EditText, Boolean>()


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

            if(binding.passwordRepeatET.text.isNotEmpty()) {
                if (binding.passwordRepeatET.text.toString() == binding.passwordET.text.toString()) {
                    binding.passwordRepeatET.error = null
                    inputsValidMap[binding.passwordRepeatET] = true
                } else {
                    binding.passwordRepeatET.error = "Passwords do not match!"
                    inputsValidMap[binding.passwordRepeatET] = false
                }
            }
        }

        binding.passwordRepeatET.addTextChangedListener {
            if (binding.passwordRepeatET.text.toString() == binding.passwordET.text.toString()) {
                binding.passwordRepeatET.error = null
                inputsValidMap[binding.passwordRepeatET] = true
            } else {
                binding.passwordRepeatET.error = "Passwords do not match!"
                inputsValidMap[binding.passwordRepeatET] = false
            }
        }


        vm.emailExistLiveData.observe(viewLifecycleOwner){
            if(it){
                binding.emailET.error = null
                inputsValidMap[binding.emailET] = true
                vm.sendPasswordResetCode(binding.emailET.text.toString())

                context?.getSharedPreferences("password-reset", Context.MODE_PRIVATE)
                    ?.edit()?.putString("email", binding.emailET.text.toString())?.apply()

                addTimer(passwordResetWaitTime)
            }
            else{
                binding.emailET.error = "Email not exist!"
                inputsValidMap[binding.emailET] = false
            }
        }

        binding.passwordResetCodeBtn.setOnClickListener {
            vm.emailExist(binding.emailET.text.toString())
        }
        binding.passwordResetBtn.setOnClickListener {
            if(inputsValidMap.all { it.value } && inputsValidMap.keys.all { it.text.toString().isNotBlank() }) {
              vm.sendPasswordReset(
                    ResetPasswordParam(
                        binding.emailET.text.toString(),
                        binding.codeET.text.toString(),
                        binding.passwordET.text.toString()
                    )
                )
                findNavController().navigate(R.id.loginFragment)
            }
        }

    }

    private fun addTimer(time: Long) {
        binding.codeET.isEnabled = true
        binding.passwordET.isEnabled = true
        binding.passwordRepeatET.isEnabled = true
        binding.passwordResetBtn.isEnabled = true
        binding.passwordResetCodeBtn.isEnabled =  false
        binding.emailET.isEnabled = false
        binding.passwordResetTimer.visibility = View.VISIBLE
        val email = context?.getSharedPreferences("password-reset", Context.MODE_PRIVATE)
            ?.getString("email", null)
        if(email != null){
            binding.emailET.setText(email)
        }

        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000 % 60
                val minutes = millisUntilFinished / 1000 / 60 % 60
                val secondsStr = if(seconds < 10) "0$seconds" else seconds.toString()
                val minutesStr = if(minutes < 10) "0$minutes" else minutes.toString()
                context?.getSharedPreferences("password-reset", Context.MODE_PRIVATE)?.edit()?.putLong(
                    "timer",
                    millisUntilFinished + Date().time
                )?.apply()

                binding.passwordResetTimer.text = "Try again in $minutesStr:$secondsStr"
            }

            override fun onFinish() {
                context?.getSharedPreferences("password-reset", Context.MODE_PRIVATE)?.edit()?.clear()?.apply()
                binding.passwordResetCodeBtn.isEnabled = true
                binding.passwordResetTimer.visibility = View.GONE
                binding.emailET.isEnabled = true
            }
        }.start()
    }

}