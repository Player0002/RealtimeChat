package com.danny.chat.presentation.di.room

import com.danny.chat.domain.repository.RoomRepository
import com.danny.chat.domain.usecase.room.AddRoomUseCase
import com.danny.chat.domain.usecase.room.AddUserUseCase
import com.danny.chat.domain.usecase.room.GetAllRoomsUseCase
import com.danny.chat.domain.usecase.room.RemoveRoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomUseCaseModule {
    @Provides
    @Singleton
    fun provideGetAllRoomUseCase(roomRepository: RoomRepository): GetAllRoomsUseCase {
        return GetAllRoomsUseCase(roomRepository)
    }

    @Provides
    @Singleton
    fun provideAddRoomUseCase(roomRepository: RoomRepository): AddRoomUseCase {
        return AddRoomUseCase(roomRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveRoomUseCase(roomRepository: RoomRepository): RemoveRoomUseCase {
        return RemoveRoomUseCase(roomRepository)
    }

    @Provides
    @Singleton
    fun provideAddUserUseCase(roomRepository: RoomRepository): AddUserUseCase {
        return AddUserUseCase(roomRepository)
    }
}