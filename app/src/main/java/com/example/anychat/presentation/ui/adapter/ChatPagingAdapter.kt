package com.example.anychat.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.anychat.R
import com.example.anychat.databinding.LeftChatItemCardViewBinding
import com.example.anychat.domain.model.dto.MessageDTO

class ChatPagingAdapter: PagingDataAdapter<MessageDTO, ChatPagingAdapter.ViewHolder>(COMPARATOR) {
    class ViewHolder(private val binding: LeftChatItemCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MessageDTO?) {
            binding.userNameTV.text = item?.username ?: "Error"
            binding.messageTextTV.text = item?.text ?: "Error getting message"
        }
        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.left_chat_item_card_view, parent, false)
                val binding = LeftChatItemCardViewBinding.bind(view)
                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MessageDTO>() {
            override fun areItemsTheSame(oldItem: MessageDTO, newItem: MessageDTO): Boolean =
                oldItem.text == newItem.text && oldItem.username == newItem.username && oldItem.createdAt == newItem.createdAt

            override fun areContentsTheSame(oldItem: MessageDTO, newItem: MessageDTO): Boolean =
                oldItem == newItem

        }
    }
}