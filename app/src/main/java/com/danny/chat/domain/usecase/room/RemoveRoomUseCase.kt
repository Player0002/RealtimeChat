package com.danny.chat.domain.usecase.room

import com.danny.chat.data.entity.Room
import com.danny.chat.domain.repository.RoomRepository

class RemoveRoomUseCase(private val roomRepository: RoomRepository) {
    suspend fun execute(uid: String, room: Room) = roomRepository.removeRoom(uid, room)
}