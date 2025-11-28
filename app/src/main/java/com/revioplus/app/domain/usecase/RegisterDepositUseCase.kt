package com.revioplus.app.domain.usecase

import com.revioplus.app.domain.model.DepositoReciclaje
import com.revioplus.app.domain.repository.ChallengeRepository
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RegisterDepositUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val recyclingRepository: RecyclingRepository,
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(cantidadBotellas: Int): DepositoReciclaje {
        require(cantidadBotellas > 0) {
            "La cantidad de botellas debe ser mayor que cero."
        }

        val user = userRepository.getCurrentUser().first()
            ?: throw IllegalStateException("No hay usuario actual")

        // Registrar el depósito en el repositorio
        val deposit = recyclingRepository.registerDeposit(
            idUsuario = user.idUsuario,
            cantidadBotellas = cantidadBotellas
        )

        // Actualizar progreso de desafío
        challengeRepository.addProgress(cantidadBotellas)

        return deposit
    }
}