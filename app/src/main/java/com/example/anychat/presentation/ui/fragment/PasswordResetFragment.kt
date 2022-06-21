package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        context?.getSharedPreferences("password-reset-timer", Context.MODE_PRIVATE)?.getLong(
            "timer",
            0
        )?.let {
            val diff = it - Date().time
            if(diff > 0 ) {
              addTimer(diff)
            }
        }


        binding.passwordResetCodeBtn.setOnClickListener {
            vm.sendPasswordResetCode(binding.emailET.text.toString())
            addTimer(passwordResetWaitTime)
        }
        binding.passwordResetBtn.setOnClickListener {
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

    private fun addTimer(time: Long) {
        binding.codeET.isEnabled = true
        binding.passwordET.isEnabled = true
        binding.passwordRepeatET.isEnabled = true
        binding.passwordResetBtn.isEnabled = true
        binding.passwordResetCodeBtn.isEnabled =  false
        binding.passwordResetTimer.visibility = View.VISIBLE
        object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000 % 60
                val minutes = millisUntilFinished / 1000 / 60 % 60
                val secondsStr = if(seconds < 10) "0$seconds" else seconds.toString()
                val minutesStr = if(minutes < 10) "0$minutes" else minutes.toString()
                context?.getSharedPreferences("password-reset-timer", Context.MODE_PRIVATE)?.edit()?.putLong(
                    "timer",
                    millisUntilFinished + Date().time
                )?.apply()

                binding.passwordResetTimer.text = "Try again in $minutesStr:$secondsStr"
            }

            override fun onFinish() {
                context?.getSharedPreferences("password-reset-timer", Context.MODE_PRIVATE)?.edit()?.remove("timer")?.apply()
                binding.passwordResetCodeBtn.isEnabled = true
                binding.passwordResetTimer.visibility = View.GONE
            }
        }.start()
    }

}