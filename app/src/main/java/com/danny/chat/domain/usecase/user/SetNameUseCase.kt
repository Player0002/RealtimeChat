package com.danny.chat.domain.usecase.user

import com.danny.chat.domain.repository.UserRepository

class SetNameUseCase(private val userRepository: UserRepository) {
    suspend fun execute(uid: String, name: String) = userRepository.setName(uid, name)
}