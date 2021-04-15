package com.danny.chat.domain.usecase.user

import com.danny.chat.domain.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class SignOutUseCase(private val userRepository: UserRepository) {
    suspend fun execute(acct : GoogleSignInClient) = userRepository.signOutGoogle(acct)
}