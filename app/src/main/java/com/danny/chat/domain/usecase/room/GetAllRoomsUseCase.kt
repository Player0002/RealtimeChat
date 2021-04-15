package com.danny.chat.domain.usecase.room

import com.danny.chat.data.entity.Room
import com.danny.chat.data.utils.Result
import com.danny.chat.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class GetAllRoomsUseCase(private val roomRepository: RoomRepository) {
    fun execute(uid: String): Flow<Result<List<Room>>> = roomRepository.getAllRooms(uid)
}