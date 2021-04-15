package com.danny.chat.presentation.viewmodel.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danny.chat.domain.usecase.chat.AddChatUseCase
import com.danny.chat.domain.usecase.chat.DeleteChatUseCase
import com.danny.chat.domain.usecase.chat.GetAllChatsUseCase
import com.danny.chat.domain.usecase.chat.UpdateChatUseCase
import com.danny.chat.domain.usecase.user.GetNameUseCase
import com.danny.chat.domain.usecase.user.GetUserUseCase

class ChatViewModelFactory(
    private val addChatUseCase: AddChatUseCase,
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getNameUseCase: GetNameUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(
            addChatUseCase,
            getAllChatsUseCase,
            getUserUseCase,
            getNameUseCase
        ) as T
    }
}