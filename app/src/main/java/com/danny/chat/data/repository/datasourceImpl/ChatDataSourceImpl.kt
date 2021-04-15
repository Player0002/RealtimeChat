package com.danny.chat.data.repository.datasourceImpl

import com.danny.chat.data.entity.Chat
import com.danny.chat.data.repository.datasource.ChatDataSource
import com.danny.chat.data.utils.Result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ChatDataSourceImpl(val database: FirebaseDatabase) : ChatDataSource {
    companion object {
        const val CHAT_REFERENCE = "chat"
    }

    @ExperimentalCoroutinesApi
    override fun getAllChats(roomId: String): Flow<Result<List<Chat>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    it.getValue(Chat::class.java)
                }.filterNotNull().filter { chat ->
                    println(chat.roomId + " / " + roomId)
                    chat.roomId == roomId
                }.sortedBy { it.timeStamp }
                println("Success ${items.size}")
                sendBlocking(Result.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                sendBlocking(Result.Error(error.toException()))
            }
        }
        database.getReference(CHAT_REFERENCE).addValueEventListener(listener)
        awaitClose {
            database.getReference(CHAT_REFERENCE).removeEventListener(listener)
        }
    }

    override suspend fun deleteChat(chatId: String) {
        database.getReference(CHAT_REFERENCE).child(chatId).removeValue()
    }

    override suspend fun editChat(chat: Chat) {
        database.getReference(CHAT_REFERENCE).child(chat.chatId.toString()).setValue(chat)
    }

    override suspend fun writeChat(chat: Chat) {
        val ref = database.getReference(CHAT_REFERENCE).push()
        chat.chatId = ref.key
        ref.setValue(chat)
    }
}