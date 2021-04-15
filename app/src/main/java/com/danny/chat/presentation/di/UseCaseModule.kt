package com.danny.chat.presentation.di

import com.danny.chat.domain.repository.UserRepository
import com.danny.chat.domain.usecase.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetSignedInUseCase(userRepository: UserRepository): GetSignedInUseCase {
        return GetSignedInUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSignOutUseCase(userRepository: UserRepository): SignOutUseCase {
        return SignOutUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSignInUseCase(userRepository: UserRepository): SignInUseCase {
        return SignInUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSetNameUseCase(userRepository: UserRepository): SetNameUseCase {
        return SetNameUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetNameUseCase(userRepository: UserRepository): GetNameUseCase {
        return GetNameUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllUsers(userRepository: UserRepository): GetAllUserUseCase {
        return GetAllUserUseCase(userRepository)
    }
}