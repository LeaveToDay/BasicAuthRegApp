package com.staynight.domain.usecase

import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun getAllUsers() = storageRepository.getAllUsers()
}