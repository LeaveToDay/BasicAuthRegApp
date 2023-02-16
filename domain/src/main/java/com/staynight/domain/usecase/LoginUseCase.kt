package com.staynight.domain.usecase

import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val storageRepository: StorageRepository) {
    fun login(email: String, password: String): Boolean {

        val emails = storageRepository.getUserEmails()
        if (emails?.contains(email) == false)
            return false

        val passwords = storageRepository.getUserPasswords()
        val userPassword =
            String.format("%s_%s", email, password)

        if (passwords?.contains(userPassword) == false)
            return false

        return true
    }
}