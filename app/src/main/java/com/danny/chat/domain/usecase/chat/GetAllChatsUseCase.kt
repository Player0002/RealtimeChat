package com.danny.chat.domain.usecase.chat

import com.danny.chat.data.entity.Chat
import com.danny.chat.data.utils.Result
import com.danny.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetAllChatsUseCase(private val chatRepository: ChatRepository) {
    fun execute(roomId: String): Flow<Result<List<Chat>>> = chatRepository.getAlLChats(roomId)
}