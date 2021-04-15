package com.danny.chat.domain.repository

import com.danny.chat.data.entity.User
import com.danny.chat.data.utils.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): FirebaseUser
    fun isSignedIn(): Boolean

    fun getAllUsers(): Flow<Result<List<User>>>
    suspend fun getName(uid: String): String
    suspend fun setName(uid: String, name: String)
    suspend fun signInGoogle(acct: GoogleSignInAccount): Result<FirebaseUser>
    suspend fun signOutGoogle(acct: GoogleSignInClient)
}