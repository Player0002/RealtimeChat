package com.danny.chat.data.repository.datasource

import com.danny.chat.data.entity.Room
import com.danny.chat.data.utils.Result
import kotlinx.coroutines.flow.Flow

interface RoomDataSource {
    fun getRooms(uid : String) : Flow<Result<List<Room>>>
    suspend fun removeRoom(uid: String, room : Room)
    suspend fun addRoom(room : Room)
    suspend fun updateRoom(room : Room)
    suspend fun getRoom(uid : String) : Result<Room>
}