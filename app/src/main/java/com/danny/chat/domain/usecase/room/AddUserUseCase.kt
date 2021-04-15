package com.danny.chat.domain.usecase.room

import com.danny.chat.data.entity.Room
import com.danny.chat.domain.repository.RoomRepository

class AddUserUseCase(private val roomRepository: RoomRepository) {
    suspend fun execute(uid: String, room: Room) {
        roomRepository.updateRoom(room.apply {
            this.members = ArrayList(this.members).apply { add(uid) }.distinct()
        })
    }
}