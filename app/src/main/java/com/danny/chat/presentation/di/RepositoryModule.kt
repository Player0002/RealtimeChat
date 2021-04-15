package com.danny.chat.presentation.di

import com.danny.chat.data.repository.ChatRepositoryImpl
import com.danny.chat.data.repository.RoomRepositoryImpl
import com.danny.chat.data.repository.UserRepositoryImpl
import com.danny.chat.data.repository.datasource.ChatDataSource
import com.danny.chat.data.repository.datasource.RoomDataSource
import com.danny.chat.data.repository.datasource.UserDataSource
import com.danny.chat.domain.repository.ChatRepository
import com.danny.chat.domain.repository.RoomRepository
import com.danny.chat.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth,
        userDataSource: UserDataSource
    ): UserRepository {
        return UserRepositoryImpl(firebaseAuth, userDataSource)
    }

    @Provides
    @Singleton
    fun provideRoomRepository(roomDataSource: RoomDataSource): RoomRepository {
        return RoomRepositoryImpl(roomDataSource)
    }

    @Provides
    @Singleton
    fun provideChatRepository(chatDataSource: ChatDataSource, roomDataSource: RoomDataSource): ChatRepository {
        return ChatRepositoryImpl(chatDataSource, roomDataSource)
    }
}