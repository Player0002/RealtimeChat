package com.danny.chat.presentation.viewmodel.name

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danny.chat.data.utils.Event
import com.danny.chat.data.utils.Transaction
import com.danny.chat.domain.usecase.user.GetUserUseCase
import com.danny.chat.domain.usecase.user.SetNameUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class NameViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val setNameUseCase: SetNameUseCase
) : ViewModel() {
    val spinner = MutableLiveData<Event<Boolean>>()
    val transaction = MutableLiveData<Event<Transaction>>()

    private fun getUser(): FirebaseUser = getUserUseCase.execute()

    fun setName(name: String) = viewModelScope.launch {
        spinner.postValue(Event(true))
        setNameUseCase.execute(getUser().uid, name)
        spinner.postValue(Event(false))
        transaction.postValue(Event(Transaction.USER))
    }
}