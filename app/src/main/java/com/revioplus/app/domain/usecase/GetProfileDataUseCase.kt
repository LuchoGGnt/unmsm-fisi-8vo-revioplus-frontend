package com.revioplus.app.domain.usecase

import com.revioplus.app.domain.model.ProfileData
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ProfileData? {
        val user = userRepository.getCurrentUser().first() ?: return null
        return ProfileData(
            usuario = user
        )
    }
}
