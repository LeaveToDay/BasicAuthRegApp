package com.staynight.domain.repository

import com.staynight.domain.model.User

interface StorageRepository {
    fun getUserEmails(): Set<String>?
    fun getUserPasswords(): Set<String>?
    fun getUserNames(): Set<String>?
    fun getUserAges(): Set<String>?
    fun setCurrentUser(email: String)
    fun getCurrentUser(): String?
    fun getUserInfo(): User
    fun deleteUser()
    fun logout()
    fun registerUser(user: User, password: String)
    fun getAllUsers(): List<User>
}