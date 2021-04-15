package com.danny.chat.presentation.di

import com.danny.chat.domain.usecase.chat.AddChatUseCase
import com.danny.chat.domain.usecase.chat.DeleteChatUseCase
import com.danny.chat.domain.usecase.chat.GetAllChatsUseCase
import com.danny.chat.domain.usecase.chat.UpdateChatUseCase
import com.danny.chat.domain.usecase.room.AddRoomUseCase
import com.danny.chat.domain.usecase.room.GetAllRoomsUseCase
import com.danny.chat.domain.usecase.user.*
import com.danny.chat.presentation.viewmodel.chat.ChatViewModelFactory
import com.danny.chat.presentation.viewmodel.main.MainViewModelFactory
import com.danny.chat.presentation.viewmodel.name.NameViewModelFactory
import com.danny.chat.presentation.viewmodel.user.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        signInUseCase: SignInUseCase,
        getNameUseCase: GetNameUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(signInUseCase, getNameUseCase)
    }

    @Provides
    @Singleton
    fun provideUserViewModelFactory(
        signOutUseCase: SignOutUseCase,
        getUserUseCase: GetUserUseCase,
        getAllRoomsUseCase: GetAllRoomsUseCase,
        addRoomUseCase: AddRoomUseCase
    ): UserViewModelFactory {
        return UserViewModelFactory(
            getUserUseCase,
            signOutUseCase,
            getAllRoomsUseCase,
            addRoomUseCase
        )
    }

    @Provides
    @Singleton
    fun provideChatViewModelFactory(
        addChatUseCase: AddChatUseCase,
        getAllChatsUseCase: GetAllChatsUseCase,
        getUserUseCase: GetUserUseCase,
        getNameUseCase: GetNameUseCase
    ): ChatViewModelFactory {
        return ChatViewModelFactory(
            addChatUseCase,
            getAllChatsUseCase,
            getUserUseCase,
            getNameUseCase
        )
    }

    @Provides
    @Singleton
    fun provideNameViewModelFactory(
        getUserUseCase: GetUserUseCase,
        setNameUseCase: SetNameUseCase
    ): NameViewModelFactory {
        return NameViewModelFactory(setNameUseCase, getUserUseCase)
    }
}