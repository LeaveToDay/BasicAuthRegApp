package com.staynight.androidlab1.ui.allusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.staynight.domain.model.User
import com.staynight.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllUsersViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {
    private val _state = MutableLiveData<List<User>>()
    val state: LiveData<List<User>> = _state

    fun getAllUsers() {
        val users = getAllUsersUseCase.getAllUsers()
        _state.value = users
    }
}