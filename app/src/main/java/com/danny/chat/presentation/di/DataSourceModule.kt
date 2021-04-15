package com.danny.chat.presentation.di

import com.danny.chat.data.repository.datasource.ChatDataSource
import com.danny.chat.data.repository.datasource.RoomDataSource
import com.danny.chat.data.repository.datasource.UserDataSource
import com.danny.chat.data.repository.datasourceImpl.ChatDataSourceImpl
import com.danny.chat.data.repository.datasourceImpl.RoomDataSourceImpl
import com.danny.chat.data.repository.datasourceImpl.UserDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideUserDataSource(
        firebaseAuth: FirebaseAuth,
        database: FirebaseDatabase
    ): UserDataSource {
        return UserDataSourceImpl(firebaseAuth, database)
    }

    @Provides
    @Singleton
    fun provideChatDataSource(database: FirebaseDatabase): ChatDataSource {
        return ChatDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun provideRoomDataSource(database: FirebaseDatabase): RoomDataSource {
        return RoomDataSourceImpl(database)
    }
}