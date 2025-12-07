package com.revioplus.app.domain.repository

import com.revioplus.app.domain.model.DepositoReciclaje
import kotlinx.coroutines.flow.Flow

data class RecyclingStats (
    val totalBotellasRecicladas: Long,
    val totalCo2AhorradoKg: Double
)

interface RecyclingRepository {
    // getRecentDeposits ahora recibe userId como parámetro obligatorio para integrarse con el UseCase que usa la API
    fun getRecentDeposits(limit: Int = 5, userId: Long): Flow<List<DepositoReciclaje>>
    
    fun getRecyclingStats(): Flow<RecyclingStats>
    
    suspend fun registerDeposit(
        idUsuario: Long,
        cantidadBotellas: Int,
        stationId: Long // <<< NUEVO PARÁMETRO
    ): DepositoReciclaje
}
