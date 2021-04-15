package com.danny.chat.presentation.di.room

import com.danny.chat.presentation.adapter.RoomAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideRoomAdapter(): RoomAdapter {
        return RoomAdapter()
    }
}