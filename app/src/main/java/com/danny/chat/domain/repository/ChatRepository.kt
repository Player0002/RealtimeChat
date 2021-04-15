package com.danny.chat.domain.repository

import com.danny.chat.data.entity.Chat
import com.danny.chat.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getAlLChats(roomId: String): Flow<Result<List<Chat>>>
    suspend fun deleteChat(chatId: String)
    suspend fun editChat(chat: Chat)
    suspend fun addChat(chat : Chat)
}