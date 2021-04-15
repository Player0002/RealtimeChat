package com.danny.chat.presentation.di.chat

import com.danny.chat.presentation.adapter.ChatAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatAdapterModule {
    @Provides
    @Singleton
    fun provideChatAdapter(): ChatAdapter {
        return ChatAdapter()
    }
}