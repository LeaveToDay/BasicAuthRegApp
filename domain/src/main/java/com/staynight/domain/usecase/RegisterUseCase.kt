package com.staynight.domain.usecase

import com.staynight.domain.model.User
import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun register(user: User, password: String): Boolean {
        val userEmails = storageRepository.getUserEmails()?.toMutableSet()
        val hasUserEmail = !userEmails?.find { it == user.email }.isNullOrEmpty()

        if (hasUserEmail)
            return false

        storageRepository.registerUser(user, password)
        return true
    }
}