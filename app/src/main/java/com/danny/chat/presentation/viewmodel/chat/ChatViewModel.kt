package com.danny.chat.presentation.viewmodel.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.danny.chat.data.entity.Chat
import com.danny.chat.data.utils.Result
import com.danny.chat.domain.usecase.chat.AddChatUseCase
import com.danny.chat.domain.usecase.chat.DeleteChatUseCase
import com.danny.chat.domain.usecase.chat.GetAllChatsUseCase
import com.danny.chat.domain.usecase.chat.UpdateChatUseCase
import com.danny.chat.domain.usecase.user.GetNameUseCase
import com.danny.chat.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChatViewModel(
    private val addChatUseCase: AddChatUseCase,
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getNameUseCase: GetNameUseCase
) : ViewModel() {

    companion object {
        const val LOG_TAG = "CHAT_VIEW_MODEL"
    }

    val username = getUserUseCase.execute().uid

    fun getChats(roomId: String) = liveData {
        getAllChatsUseCase.execute(roomId).collect {
            if (it is Result.Success) emit(it.data.sortedBy { it.timeStamp })
            else {
                Log.e(LOG_TAG, "Error : $it")
            }
        }
    }

    fun addChat(chat: Chat) = viewModelScope.launch {
        chat.senderName = getNameUseCase.execute(chat.sender!!)
        addChatUseCase.execute(chat)
    }

}