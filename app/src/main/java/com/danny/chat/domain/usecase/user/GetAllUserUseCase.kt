package com.danny.chat.domain.usecase.user

import com.danny.chat.data.entity.User
import com.danny.chat.data.utils.Result
import com.danny.chat.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUserUseCase(private val userRepository: UserRepository) {
    fun execute(): Flow<Result<List<User>>> = userRepository.getAllUsers()
}