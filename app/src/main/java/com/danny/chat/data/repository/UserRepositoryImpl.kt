package com.danny.chat.data.repository

import com.danny.chat.data.await
import com.danny.chat.data.entity.User
import com.danny.chat.data.repository.datasource.UserDataSource
import com.danny.chat.data.utils.Result
import com.danny.chat.domain.repository.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val client: FirebaseAuth,
    private val userDataSource: UserDataSource
) : UserRepository {
    override fun getUser(): FirebaseUser {
        return userDataSource.getCurrentUser()
    }

    override fun isSignedIn(): Boolean {
        return userDataSource.isSignedIn()
    }

    override fun getAllUsers(): Flow<Result<List<User>>> = userDataSource.getAllUser()

    override suspend fun getName(uid: String): String {
        val str = userDataSource.getNameFromCache(uid)
        if (str.isEmpty()) {
            return userDataSource.getNameFromDB(uid)
        }
        return str;
    }

    override suspend fun setName(uid: String, name: String) {
        userDataSource.setName(uid, name)
    }

    @ExperimentalCoroutinesApi
    override suspend fun signInGoogle(acct: GoogleSignInAccount): Result<FirebaseUser> {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        return try {
            when (val result = client.signInWithCredential(credential).await()) {
                is Result.Success -> {
                    Result.Success(result.data.user)
                }
                is Result.Canceled -> {
                    Result.Canceled(result.exception)
                }
                is Result.Error -> {
                    Result.Error(result.exception)
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun signOutGoogle(acct: GoogleSignInClient) {
        acct.signOut().await()
        client.signOut()
    }

}