package com.danny.chat.presentation.di.chat

import com.danny.chat.domain.repository.ChatRepository
import com.danny.chat.domain.usecase.chat.AddChatUseCase
import com.danny.chat.domain.usecase.chat.DeleteChatUseCase
import com.danny.chat.domain.usecase.chat.GetAllChatsUseCase
import com.danny.chat.domain.usecase.chat.UpdateChatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatUseCaseModule {
    @Provides
    @Singleton
    fun provideAddChatUseCase(chatRepository: ChatRepository): AddChatUseCase {
        return AddChatUseCase(chatRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteChatUseCase(chatRepository: ChatRepository): DeleteChatUseCase {
        return DeleteChatUseCase(chatRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllChatsUseCase(chatRepository: ChatRepository): GetAllChatsUseCase {
        return GetAllChatsUseCase(chatRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateChatUseCase(chatRepository: ChatRepository): UpdateChatUseCase {
        return UpdateChatUseCase(chatRepository)
    }
}