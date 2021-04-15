package com.danny.chat.data.repository

import com.danny.chat.data.entity.Room
import com.danny.chat.data.repository.datasource.RoomDataSource
import com.danny.chat.data.utils.Result
import com.danny.chat.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class RoomRepositoryImpl(private val roomDataSource: RoomDataSource) : RoomRepository {
    override fun getAllRooms(uid: String): Flow<Result<List<Room>>> = roomDataSource.getRooms(uid)
    override suspend fun removeRoom(uid: String, room: Room) = roomDataSource.removeRoom(uid, room)
    override suspend fun addRoom(room: Room) = roomDataSource.addRoom(room)
    override suspend fun updateRoom(room: Room) {
        roomDataSource.updateRoom(room)
    }
}