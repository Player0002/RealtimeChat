package com.danny.chat.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.danny.chat.data.entity.Chat
import com.danny.chat.data.entity.Room
import com.danny.chat.databinding.ActivityChatBinding
import com.danny.chat.presentation.adapter.ChatAdapter
import com.danny.chat.presentation.viewmodel.chat.ChatViewModel
import com.danny.chat.presentation.viewmodel.chat.ChatViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding

    lateinit var room: Room

    @Inject
    lateinit var factory: ChatViewModelFactory

    @Inject
    lateinit var chatAdapter: ChatAdapter

    private val viewModel: ChatViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        room = (intent.extras?.get("room") ?: throw Exception("Error Room is null!")) as Room

        binding.title.text = room.title

        chatAdapter.setSender(viewModel.username)

        binding.chatRecycler.adapter = chatAdapter
        binding.chatRecycler.layoutManager = LinearLayoutManager(this)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel.getChats(room.roomId!!).observe(this) {
            chatAdapter.list.submitList(it)
            binding.chatRecycler.smoothScrollToPosition(chatAdapter.itemCount)
            binding.messageBox.setText("")
        }
        binding.send.setOnClickListener {
            val str = binding.messageBox.text.toString()
            if (str.isNotEmpty())
                viewModel.addChat(
                    Chat(
                        null,
                        room.roomId,
                        viewModel.username,
                        null,
                        binding.messageBox.text.toString(),
                        Calendar.getInstance().timeInMillis
                    )
                )
            else
                Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}