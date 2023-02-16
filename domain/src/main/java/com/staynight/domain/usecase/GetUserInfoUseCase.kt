package com.staynight.domain.usecase

import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    fun getUserInfo() = storageRepository.getUserInfo()
}