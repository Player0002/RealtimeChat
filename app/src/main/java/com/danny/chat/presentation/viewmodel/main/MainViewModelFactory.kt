package com.danny.chat.presentation.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danny.chat.domain.usecase.user.GetNameUseCase
import com.danny.chat.domain.usecase.user.SignInUseCase

class MainViewModelFactory(
    private val signInUseCase: SignInUseCase,
    private val getNameUseCase: GetNameUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(signInUseCase, getNameUseCase) as T
    }
}