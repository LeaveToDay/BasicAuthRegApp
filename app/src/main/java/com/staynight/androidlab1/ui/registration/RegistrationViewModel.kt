package com.staynight.androidlab1.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.domain.model.User
import com.staynight.domain.usecase.RegisterUseCase
import com.staynight.domain.usecase.SetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val setCurrentUserUseCase: SetCurrentUserUseCase
) : ViewModel() {
    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun register(user: User, password: String) {
        val registerResult = registerUseCase.register(user, password)

        if (registerResult)
            setCurrentUser(user.email)

        _state.value = registerResult

    }

    private fun setCurrentUser(email: String) {
        setCurrentUserUseCase.setCurrentUser(email)
    }
}