package com.danny.chat.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danny.chat.data.entity.Chat
import com.danny.chat.databinding.ChatReceiverLayoutBinding
import com.danny.chat.databinding.ChatSendLayoutBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = object : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.chatId == newItem.chatId
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }
    }

    inner class ChatHolder(private val binding: ChatSendLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindSender(chat: Chat) {
            binding.name.text = chat.senderName
            binding.contents.text = chat.message
        }
    }

    inner class ChatReceiveHolder(private val binding: ChatReceiverLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindReceiver(chat: Chat) {
            binding.name.text = chat.senderName
            binding.contents.text = chat.message
        }
    }

    val list = AsyncListDiffer(this, differ)

    private var senderId: String? = null;

    fun setSender(sender: String) {
        senderId = sender
    }


    override fun getItemViewType(position: Int): Int {
        return if (list.currentList[position].sender == senderId) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                ChatHolder(ChatSendLayoutBinding.inflate(inflater, parent, false))
            }
            else -> {
                ChatReceiveHolder(ChatReceiverLayoutBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> (holder as ChatHolder).bindSender(list.currentList[position])
            1 -> (holder as ChatReceiveHolder).bindReceiver(list.currentList[position])
        }
    }

    override fun getItemCount(): Int = list.currentList.size
}