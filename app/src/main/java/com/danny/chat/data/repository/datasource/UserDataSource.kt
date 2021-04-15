package com.danny.chat.data.repository.datasource

import com.danny.chat.data.entity.User
import com.danny.chat.data.utils.Result
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun getCurrentUser(): FirebaseUser
    fun isSignedIn(): Boolean
    suspend fun getNameFromDB(uid: String): String
    suspend fun getNameFromCache(uid: String): String
    fun getAllUser(): Flow<Result<List<User>>>
    suspend fun setName(uid: String, name: String)
}