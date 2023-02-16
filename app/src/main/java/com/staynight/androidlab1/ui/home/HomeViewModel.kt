package com.staynight.androidlab1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.domain.model.User
import com.staynight.domain.usecase.DeleteUserUseCase
import com.staynight.domain.usecase.GetUserInfoUseCase
import com.staynight.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {
    private val _state = MutableLiveData<User>()
    val state: LiveData<User> = _state

    fun getUserInfo() {
        val userInfo = getUserInfoUseCase.getUserInfo()
        _state.value = userInfo
    }

    fun logout() {
        logoutUseCase.logout()
    }

    fun deleteUser() {
        deleteUserUseCase.deleteUser()
    }
}
