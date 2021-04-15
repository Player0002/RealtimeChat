package com.danny.chat.domain.usecase.user

import com.danny.chat.domain.repository.UserRepository

class GetSignedInUseCase (private val userRepository: UserRepository){
    fun execute() : Boolean = userRepository.isSignedIn()
}