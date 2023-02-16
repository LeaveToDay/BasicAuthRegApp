package com.staynight.domain.usecase

import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun logout() = storageRepository.logout()
}