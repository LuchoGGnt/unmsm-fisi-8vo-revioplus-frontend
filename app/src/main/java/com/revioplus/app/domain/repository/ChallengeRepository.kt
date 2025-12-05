package com.revioplus.app.domain.repository

import com.revioplus.app.domain.model.Desafio
import kotlinx.coroutines.flow.Flow

data class ChallengeProgress(
    val botellasActuales: Int,
    val metaBotellas: Int
)

interface ChallengeRepository {
    fun getCurrentChallenge(): Flow<Desafio?>
    fun getCurrentChallengeProgress(): Flow<ChallengeProgress>
    suspend fun addProgress(userId: Long, bottles: Int)
}