package com.example.anychat.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.anychat.R
import com.example.anychat.data.enums.Enums
import com.example.anychat.domain.model.dto.MessageDTO
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class ChatPagingAdapter(
    private val mUserName: String,
    private val localDateTime: LocalDateTime
) : PagingDataAdapter<MessageDTO, RecyclerView.ViewHolder>(COMPARATOR) {


    internal class YourMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userNameTV: TextView
        var messageTextTV: TextView
        var messageTimeStampTV: TextView
        var userImageIV: ImageView


        init {
            userNameTV = itemView.findViewById(R.id.userNameTV)
            messageTextTV = itemView.findViewById(R.id.messageTextTV)
            userImageIV = itemView.findViewById(R.id.userImageIV)
            messageTimeStampTV = itemView.findViewById(R.id.messageTimeStampTV)
            userImageIV.setOnClickListener {
                val bundle = bundleOf("profileUsername" to userNameTV.text)
                itemView.findNavController().navigate(R.id.profileFragment, bundle)
            }
        }
    }

    internal class MyMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userNameTV: TextView
        var messageTextTV: TextView
        var messageTimeStampTV: TextView
        var userImageIV: ImageView


        init {
            userNameTV = itemView.findViewById(R.id.userNameTV)
            messageTextTV = itemView.findViewById(R.id.messageTextTV)
            userImageIV = itemView.findViewById(R.id.userImageIV)
            messageTimeStampTV = itemView.findViewById(R.id.messageTimeStampTV)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.username == mUserName) {
            Enums.ViewType.MyMessage.ordinal
        } else {
            Enums.ViewType.YourMessage.ordinal
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType != Enums.ViewType.MyMessage.ordinal) {
            initYourMessage(holder as YourMessageViewHolder, position, localDateTime)
        } else {
            initMyMessage(holder as MyMessageViewHolder, position, localDateTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == Enums.ViewType.MyMessage.ordinal) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.right_chat_item_card_view, parent, false)
            MyMessageViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.left_chat_item_card_view, parent, false)
            YourMessageViewHolder(view)
        }
    }

    private fun initMyMessage(holder: MyMessageViewHolder, pos: Int, date: LocalDateTime) {
        val message: MessageDTO? = getItem(pos)
        holder.messageTextTV.text = message?.text
        holder.userNameTV.text = message?.username
        if(message?.profileBitmap != null)
        holder.userImageIV.setImageBitmap(message.profileBitmap)

        val dateTime: LocalDateTime = convertToLocalZoneTime(message)

        if (dateTime.dayOfYear < date.dayOfYear || dateTime.year < date.year) {
            holder.messageTimeStampTV.text = DateTimeFormatter.ofPattern("MM-dd HH:mm").format(dateTime)
        } else {
            holder.messageTimeStampTV.text = DateTimeFormatter.ofPattern("HH:mm").format(dateTime)
        }
    }

    private fun convertToLocalZoneTime(message: MessageDTO?): LocalDateTime {
        val oldDateTime = LocalDateTime.parse(message?.createdAt)
        val oldZone = ZoneId.of("UTC")

        val newZone = TimeZone.getDefault().toZoneId()
        val dateTime: LocalDateTime = oldDateTime.atZone(oldZone)
            .withZoneSameInstant(newZone)
            .toLocalDateTime()
        return dateTime
    }

    private fun initYourMessage(holder: YourMessageViewHolder, pos: Int, date: LocalDateTime) {
        val message: MessageDTO? = getItem(pos)
        holder.messageTextTV.text = message?.text
        holder.userNameTV.text = message?.username
        if(message?.profileBitmap != null)
        holder.userImageIV.setImageBitmap(message.profileBitmap)

        val dateTime: LocalDateTime = convertToLocalZoneTime(message)
        if (dateTime.dayOfYear < date.dayOfYear || dateTime.year < date.year) {
            holder.messageTimeStampTV.text = DateTimeFormatter.ofPattern("MM-dd HH:mm").format(dateTime)
        } else {
            holder.messageTimeStampTV.text = DateTimeFormatter.ofPattern("HH:mm").format(dateTime)
        }
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