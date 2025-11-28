package com.revioplus.app.domain.repository

import com.revioplus.app.domain.model.DepositoReciclaje
import kotlinx.coroutines.flow.Flow

data class RecyclingStats (
    val totalBotellasRecicladas: Long,
    val totalCo2AhorradoKg: Double
)

interface RecyclingRepository {
    fun getRecentDeposits(limit: Int = 5): Flow<List<DepositoReciclaje>>
    fun getRecyclingStats(): Flow<RecyclingStats>
    suspend fun registerDeposit(
        idUsuario: Long,
        cantidadBotellas: Int
    ): DepositoReciclaje
}