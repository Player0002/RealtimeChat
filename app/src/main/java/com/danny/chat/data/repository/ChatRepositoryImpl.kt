package com.danny.chat.data.repository

import com.danny.chat.data.entity.Chat
import com.danny.chat.data.repository.datasource.ChatDataSource
import com.danny.chat.data.repository.datasource.RoomDataSource
import com.danny.chat.data.utils.Result
import com.danny.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ChatRepositoryImpl(
    private val chatDataSource: ChatDataSource,
    private val roomDataSource: RoomDataSource
) : ChatRepository {
    override fun getAlLChats(roomId: String): Flow<Result<List<Chat>>> =
        chatDataSource.getAllChats(roomId)

    override suspend fun deleteChat(chatId: String) = chatDataSource.deleteChat(chatId)

    override suspend fun editChat(chat: Chat) = chatDataSource.editChat(chat)

    override suspend fun addChat(chat: Chat) {
        chat.roomId?.let {
            val room = roomDataSource.getRoom(it);
            if (room is Result.Success) {
                roomDataSource.updateRoom(room.data.apply {
                    lastMessage = chat.message
                })

                chatDataSource.writeChat(chat)
            }
        }
    }
}