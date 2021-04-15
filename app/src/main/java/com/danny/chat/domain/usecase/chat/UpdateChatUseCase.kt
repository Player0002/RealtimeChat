package com.danny.chat.domain.usecase.chat

import com.danny.chat.data.entity.Chat
import com.danny.chat.domain.repository.ChatRepository

class UpdateChatUseCase(private val chatRepository: ChatRepository) {
    suspend fun execute(chat: Chat) = chatRepository.editChat(chat)
}