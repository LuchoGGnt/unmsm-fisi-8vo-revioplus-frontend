package com.revioplus.app.domain.usecase

import com.revioplus.app.domain.model.HomeDashboard
import com.revioplus.app.domain.repository.ChallengeRepository
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetHomeDashboardUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val recyclingRepository: RecyclingRepository,
    private val challengeRepository: ChallengeRepository
) {
    // Llamado desde el ViewModel dentro de una corrutina
    suspend operator fun invoke(): HomeDashboard? {
        val usuario = userRepository.getCurrentUser().first() ?: return null
        val stats = recyclingRepository.getRecyclingStats().first()
        val challenge = challengeRepository.getCurrentChallenge().first()
        val progress = challengeRepository.getCurrentChallengeProgress().first()
        val depositos = recyclingRepository.getRecentDeposits(limit = 5).first()

        return HomeDashboard(
            usuario = usuario,
            recyclingStats = stats,
            desafioActual = challenge,
            progresoDesafio = progress,
            depositosRecientes = depositos
        )
    }
}