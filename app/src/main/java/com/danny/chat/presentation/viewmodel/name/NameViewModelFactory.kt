package com.danny.chat.presentation.viewmodel.name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danny.chat.domain.usecase.user.GetUserUseCase
import com.danny.chat.domain.usecase.user.SetNameUseCase

class NameViewModelFactory(
    private val setNameUseCase: SetNameUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NameViewModel(getUserUseCase, setNameUseCase) as T
    }

}