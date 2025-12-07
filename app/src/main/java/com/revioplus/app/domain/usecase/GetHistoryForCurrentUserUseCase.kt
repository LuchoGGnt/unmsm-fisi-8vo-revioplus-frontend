package com.revioplus.app.domain.usecase

import com.revioplus.app.domain.model.DepositoReciclaje
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetHistoryForCurrentUserUseCase @Inject constructor(
    val recyclingRepo : RecyclingRepository,
    val userRepo : UserRepository
) {
    suspend operator fun invoke(): List<DepositoReciclaje>? {
        val user = userRepo.getCurrentUser().first() ?: return null
        return recyclingRepo.getRecentDeposits(userId = user.idUsuario).first()
    }
}