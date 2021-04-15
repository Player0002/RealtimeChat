package com.danny.chat.data.repository.datasourceImpl

import android.util.Log
import com.danny.chat.data.await
import com.danny.chat.data.entity.User
import com.danny.chat.data.repository.datasource.UserDataSource
import com.danny.chat.data.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserDataSourceImpl(private val client: FirebaseAuth, private val database: FirebaseDatabase) :
    UserDataSource {

    companion object {
        const val DATABASE_NAME = "name"
        const val LOG_TAG = "USER_DATA_SOURCE"
    }

    private val cache = HashMap<String, String>();

    override fun getCurrentUser(): FirebaseUser {
        return client.currentUser ?: throw Exception("Call get user when not login")
    }

    override fun isSignedIn(): Boolean {
        return client.currentUser != null
    }

    @ExperimentalCoroutinesApi
    override suspend fun getNameFromDB(uid: String): String {
        when (val result = database.getReference(DATABASE_NAME).child(uid).get().await()) {
            is Result.Success -> {
                val str = result.data.getValue(String::class.java) ?: ""
                cache[uid] = str
                return str
            }
            is Result.Error -> {
                Log.e(LOG_TAG, "ERROR ${result.exception}")
            }
        }
        return ""
    }

    override suspend fun getNameFromCache(uid: String): String {
        return cache.getOrDefault(uid, "")
    }

    @ExperimentalCoroutinesApi
    override fun getAllUser(): Flow<Result<List<User>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val filtered = snapshot.children.map {
                    it.getValue(User::class.java)
                }.filterNotNull()
                sendBlocking(Result.Success(filtered))
            }

            override fun onCancelled(error: DatabaseError) {
                sendBlocking(Result.Canceled(error.toException()))
            }
        }
        database.getReference(DATABASE_NAME).addValueEventListener(listener)
        awaitClose {
            database.getReference(DATABASE_NAME).removeEventListener(listener)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun setName(uid: String, name: String) {
        database.getReference(DATABASE_NAME).child(uid).setValue(name)
    }

}