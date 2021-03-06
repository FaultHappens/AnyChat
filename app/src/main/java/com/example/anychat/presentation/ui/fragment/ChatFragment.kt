package com.example.anychat.presentation.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anychat.databinding.FragmentChatBinding
import com.example.anychat.presentation.ui.adapter.ChatPagingAdapter
import com.example.anychat.presentation.vm.ChatFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.naiksoftware.stomp.Stomp
import java.time.LocalDateTime


class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    lateinit var chatAdapter: ChatPagingAdapter

    private val vm: ChatFragmentVM by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    lateinit var accessToken: String

    val mStompClient by lazy {
        Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            "ws://192.168.191.58:8080/websocket",
            mapOf("Authorization" to "Bearer $accessToken")
        )
    }


    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = context?.getSharedPreferences("token", Context.MODE_PRIVATE)
            ?.getString("username", "error")
            ?.let { ChatPagingAdapter(it, LocalDateTime.now()) }!!

        binding.messagesRV.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            adapter = chatAdapter
        }


        accessToken = context?.getSharedPreferences("token", Context.MODE_PRIVATE)
            ?.getString("access_token", null) ?: return

        Log.d("TOKEN", accessToken)

        this.lifecycleScope.launchWhenCreated {
            vm.getChatMessages().collectLatest { pagingData ->
                chatAdapter.submitData(pagingData)
            }
        }

        mStompClient.connect()

        Handler().postDelayed({
            vm.getOnline()
        }, 1000)

        mStompClient.topic("/topic/1/messages").subscribe {
            this.lifecycleScope.launch(Dispatchers.IO) {
                vm.getChatMessages().collectLatest { pagingData ->
                    chatAdapter.submitData(pagingData)
                    withContext(Dispatchers.Main) {
                        scrollCallBack()
                    }
                }
            }
        }

        mStompClient.topic("/topic/1/online").subscribe {
            requireActivity().runOnUiThread {
                binding.onlineTV.text = "Online: ${it.payload}"
            }

        }
        vm.onlineLiveData.observe(viewLifecycleOwner) {
            binding.onlineTV.text = "Online: $it"
        }


        binding.sendBttn.setOnClickListener {
            val message = binding.messageTextET.text?.toString()
            if (message != null) {
                mStompClient.send("/app/1/messages", message).subscribe()
                binding.messageTextET.text.clear()
            }

        }
    }

    fun scrollCallBack() {
        Handler().postDelayed({
            binding.messagesRV.scrollToPosition(0)
        }, 500)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mStompClient.disconnect()
    }

}