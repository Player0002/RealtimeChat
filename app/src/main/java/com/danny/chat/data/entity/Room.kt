package com.danny.chat.data.entity

import java.io.Serializable

data class Room(
    var roomId: String? = null,
    var members: List<String>? = null,
    val title: String? = null,
    var lastMessage: String? = null,
    var timeStamp: Long? = null
) : Serializable