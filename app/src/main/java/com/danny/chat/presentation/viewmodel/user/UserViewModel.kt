package com.danny.chat.presentation.viewmodel.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.danny.chat.data.entity.Room
import com.danny.chat.data.utils.Event
import com.danny.chat.data.utils.Result
import com.danny.chat.data.utils.Transaction
import com.danny.chat.domain.usecase.room.AddRoomUseCase
import com.danny.chat.domain.usecase.room.GetAllRoomsUseCase
import com.danny.chat.domain.usecase.user.GetUserUseCase
import com.danny.chat.domain.usecase.user.SignOutUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UserViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val addRoomUseCase: AddRoomUseCase,
) : ViewModel() {

    companion object {
        const val LOG_TAG = "USER_VIEW_MODEL"
    }

    val transaction = MutableLiveData<Event<Transaction>>()

    fun getUser(): FirebaseUser = getUserUseCase.execute()
    fun signOut(acct: GoogleSignInClient) = viewModelScope.launch {
        signOutUseCase.execute(acct)
        transaction.postValue(Event(Transaction.MAIN))
    }

    fun getAllRooms(uid: String) = liveData {
        getAllRoomsUseCase.execute(uid).collect {
            if (it is Result.Success) {
                emit(it.data)
            } else {
                Log.e(LOG_TAG, it.toString())
            }
        }
    }

    fun addRoom(room: Room) = viewModelScope.launch {
        addRoomUseCase.execute(room)
    }

}