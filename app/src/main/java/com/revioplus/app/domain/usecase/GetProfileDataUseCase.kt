package com.revioplus.app.domain.usecase

import com.revioplus.app.domain.model.ProfileData
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val recyclingRepository: RecyclingRepository
) {
    suspend operator fun invoke(): ProfileData? {
        val user = userRepository.getCurrentUser().first() ?: return null
        val stats = recyclingRepository.getRecyclingStats().first()

        return ProfileData(
            usuario = user,
            recyclingStats = stats
        )
    }
}