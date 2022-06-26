package com.example.anychat.presentation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anychat.databinding.FragmentChatBinding
import com.example.anychat.domain.model.dto.MessageDTO
import com.example.anychat.presentation.ui.adapter.ChatPagingAdapter
import com.example.anychat.presentation.vm.ChatFragmentVM
import com.example.anychat.presentation.vm.LoginFragmentVM
import com.google.gson.Gson
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.naiksoftware.stomp.Stomp


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatAdapter = ChatPagingAdapter()

        binding.messagesRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }



        val accessToken = context?.getSharedPreferences("token", Context.MODE_PRIVATE)
            ?.getString("access_token", null) ?: return

        Log.d("TOKEN", accessToken)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.getChatMessages().collectLatest { pagingData ->
                chatAdapter.submitData(pagingData)
                Log.d("PAGINGDATA", "pagingData.toString()")
            }
        }

        val mStompClient = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            "ws://192.168.191.58:8080/websocket",
            mapOf("Authorization" to "Bearer $accessToken")
        )
        mStompClient.connect()
        mStompClient.topic("/topic/1/messages").subscribe{
            //TODO HANDLE MESSAGE
            val messageDTO = Gson().fromJson(it.payload, MessageDTO::class.java)
            Log.d("socket", messageDTO.toString())
        }




        binding.sendBttn.setOnClickListener {
            val message = binding.messageTextET.text?.toString()
            if(message != null)
            mStompClient.send("/app/1/messages", message).subscribe()

        }
    }
}