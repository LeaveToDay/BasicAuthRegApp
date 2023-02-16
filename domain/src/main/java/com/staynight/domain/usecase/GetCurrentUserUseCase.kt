package com.staynight.domain.usecase

import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun getCurrentUser(): String? = storageRepository.getCurrentUser()
}