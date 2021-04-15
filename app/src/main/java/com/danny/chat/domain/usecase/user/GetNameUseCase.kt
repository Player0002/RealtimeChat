package com.danny.chat.domain.usecase.user

import com.danny.chat.domain.repository.UserRepository

class GetNameUseCase(private val userRepository: UserRepository) {
    suspend fun execute(uid: String): String = userRepository.getName(uid)
}