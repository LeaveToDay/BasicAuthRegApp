package com.staynight.androidlab1.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.domain.usecase.GetCurrentUserUseCase
import com.staynight.domain.usecase.LoginUseCase
import com.staynight.domain.usecase.SetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val setCurrentUserUseCase: SetCurrentUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun isAuth() = !getCurrentUserUseCase.getCurrentUser().isNullOrEmpty()

    fun login(email: String, password: String) {
        val loginResult = loginUseCase.login(email, password)

        if (loginResult)
            setCurrentUser(email)

        _state.value = loginResult
    }

    private fun setCurrentUser(email: String) {
        setCurrentUserUseCase.setCurrentUser(email)
    }
}