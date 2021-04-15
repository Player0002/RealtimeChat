package com.danny.chat.data.entity

data class Chat(
    var chatId: String? = null,
    val roomId: String? = null,
    var sender: String? = null,
    var senderName: String? = null,
    val message: String? = null,
    val timeStamp: Long? = null
)