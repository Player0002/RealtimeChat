package com.danny.chat.data.repository.datasource

import com.danny.chat.data.entity.Chat
import com.danny.chat.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface ChatDataSource {
    fun getAllChats(roomId: String): Flow<Result<List<Chat>>>
    suspend fun deleteChat(chatId: String)
    suspend fun editChat(chat: Chat)
    suspend fun writeChat(chat: Chat)
}
