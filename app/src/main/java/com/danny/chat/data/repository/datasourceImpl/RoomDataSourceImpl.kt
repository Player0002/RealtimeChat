package com.danny.chat.data.repository.datasourceImpl

import com.danny.chat.data.await
import com.danny.chat.data.entity.Room
import com.danny.chat.data.repository.datasource.RoomDataSource
import com.danny.chat.data.utils.Result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RoomDataSourceImpl(private val firebaseDatabase: FirebaseDatabase) : RoomDataSource {
    companion object {
        const val ROOM_REFERENCE = "room"
    }

    @ExperimentalCoroutinesApi
    override fun getRooms(uid: String): Flow<Result<List<Room>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    it.getValue(Room::class.java)
                }.filterNotNull().filter { room ->
                    room.members?.contains(uid) ?: false
                }
                sendBlocking(Result.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                sendBlocking(Result.Error(error.toException()))
            }
        }
        firebaseDatabase.getReference(ROOM_REFERENCE).addValueEventListener(listener)
        awaitClose {
            firebaseDatabase.getReference(ROOM_REFERENCE).removeEventListener(listener)
        }
    }

    override suspend fun removeRoom(uid: String, room: Room) {
        firebaseDatabase.getReference(ROOM_REFERENCE).child(room.roomId.toString())
            .setValue(
                Room(
                    room.roomId,
                    room.members?.filter { it != uid },
                    room.title,
                    room.lastMessage,
                    room.timeStamp
                )
            )
    }

    override suspend fun addRoom(room: Room) {
        val ref = firebaseDatabase.getReference(ROOM_REFERENCE).push()
        room.roomId = ref.key
        ref.setValue(room)
    }

    override suspend fun updateRoom(room: Room) {
        firebaseDatabase.getReference(ROOM_REFERENCE).child(room.roomId.toString()).setValue(room)
    }

    @ExperimentalCoroutinesApi
    override suspend fun getRoom(uid: String): Result<Room> {
        when (
            val result = firebaseDatabase.getReference(ROOM_REFERENCE).get().await()) {
            is Result.Success -> {
                val room =
                    result.data.children.map { it.getValue(Room::class.java) }.filterNotNull()
                        .first { cmp -> cmp.roomId == uid }
                return Result.Success(room)
            }
        }
        return Result.Error(Exception("Cannot find room"))
    }
}