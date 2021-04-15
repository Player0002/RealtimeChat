package com.danny.chat.domain.usecase.user

import com.danny.chat.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class GetUserUseCase(private val userRepository: UserRepository) {
    fun execute(): FirebaseUser {
        return userRepository.getUser()
    }
}