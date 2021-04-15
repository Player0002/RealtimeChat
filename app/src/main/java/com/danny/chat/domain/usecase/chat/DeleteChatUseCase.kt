package com.danny.chat.domain.usecase.chat

import com.danny.chat.domain.repository.ChatRepository

class DeleteChatUseCase(private val chatRepository: ChatRepository) {
    suspend fun execute(chatId: String) = chatRepository.deleteChat(chatId)
}