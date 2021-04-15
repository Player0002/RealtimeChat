package com.danny.chat.presentation.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danny.chat.data.utils.Event
import com.danny.chat.data.utils.Result
import com.danny.chat.data.utils.Transaction
import com.danny.chat.domain.usecase.user.GetNameUseCase
import com.danny.chat.domain.usecase.user.SignInUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.launch

class MainViewModel(
    private val signInUseCase: SignInUseCase,
    private val getNameUseCase: GetNameUseCase
) : ViewModel() {

    val toast = MutableLiveData<Event<String>>();
    val spinner = MutableLiveData<Boolean>();
    val transaction = MutableLiveData<Event<Transaction>>();

    fun loginUser(userAuth: GoogleSignInAccount) = viewModelScope.launch {
        spinner.postValue(true)
        when (val result = signInUseCase.execute(userAuth)) {
            is Result.Success -> {
                val name = getNameUseCase.execute(result.data.uid)
                if (name.isEmpty())
                    transaction.postValue(Event(Transaction.NAME))
                else
                    transaction.postValue(Event(Transaction.USER))
                println("Result success")
            }
            is Result.Error -> {
                toast.postValue(Event("Err : ${result.exception.message}"))
                println("Err : ${result.exception.message}")
            }
            is Result.Canceled -> {
                toast.postValue(Event("Cancel : ${result.exception?.message}"))
                println("Cancel : ${result.exception?.message}")
            }
        }
        spinner.postValue(false)
    }

}