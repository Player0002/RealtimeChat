package com.danny.chat.presentation.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danny.chat.domain.usecase.user.GetUserUseCase
import com.danny.chat.domain.usecase.user.SignOutUseCase
import com.danny.chat.domain.usecase.room.AddRoomUseCase
import com.danny.chat.domain.usecase.room.GetAllRoomsUseCase

class UserViewModelFactory(
    private val getUserUseCase: GetUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getAllRoomsUseCase: GetAllRoomsUseCase,
    private val addRoomUseCase: AddRoomUseCase
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(
            signOutUseCase,
            getUserUseCase,
            getAllRoomsUseCase,
            addRoomUseCase
        ) as T
    }
}