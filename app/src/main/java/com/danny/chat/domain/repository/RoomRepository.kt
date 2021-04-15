package com.danny.chat.domain.repository

import com.danny.chat.data.entity.Room
import com.danny.chat.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getAllRooms(uid: String): Flow<Result<List<Room>>>
    suspend fun removeRoom(uid: String, room: Room)
    suspend fun addRoom(room : Room)
    suspend fun updateRoom(room : Room)
}