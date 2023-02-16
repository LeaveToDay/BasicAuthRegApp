package com.staynight.domain.usecase

import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class SetCurrentUserUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun setCurrentUser(email: String) = storageRepository.setCurrentUser(email)
}