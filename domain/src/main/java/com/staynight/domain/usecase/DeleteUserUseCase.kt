package com.staynight.domain.usecase

import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun deleteUser() = storageRepository.deleteUser()
}