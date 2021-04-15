package com.danny.chat.domain.usecase.user

import com.danny.chat.data.utils.Result
import com.danny.chat.domain.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class SignInUseCase(private val userRepository: UserRepository) {
    suspend fun execute(account: GoogleSignInAccount): Result<FirebaseUser> {
        return userRepository.signInGoogle(account)
    }
}