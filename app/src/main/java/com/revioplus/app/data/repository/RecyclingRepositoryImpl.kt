package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.local.SessionManager
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.DepositRequestDto
import com.revioplus.app.data.remote.dto.toDomain
import com.revioplus.app.domain.model.DepositoReciclaje
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.RecyclingStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecyclingRepositoryImpl @Inject constructor(
    private val api: ReVioApi,
    private val sessionManager: SessionManager
) : RecyclingRepository {

    override fun getRecentDeposits(limit: Int, userId: Long): Flow<List<DepositoReciclaje>> = flow {
        // Usamos el ID de sesión para garantizar seguridad
        // No hay sesión activa o la sesion no coincide con la sesión actual
        val currentUserId = sessionManager.userId.first()
        if (currentUserId == null || userId != currentUserId) return@flow emit(emptyList())

        val dtos = api.getRecyclingHistory(currentUserId)
        val deposits = dtos.map { it.toDomain() }
            .sortedByDescending { it.fechaHoraMillis }
            .take(limit)
            
        emit(deposits)
    }.catch { e ->
        Log.e("RecyclingRepo", "Error fetching deposits", e)
        emit(emptyList())
    }

    override fun getRecyclingStats(): Flow<RecyclingStats> = flow {
        emit(RecyclingStats(0, 0.0))
    }.catch { e ->
         Log.e("RecyclingRepo", "Error fetching stats", e)
         emit(RecyclingStats(0, 0.0))
    }

    override suspend fun registerDeposit(
        idUsuario: Long,
        cantidadBotellas: Int,
        stationId: Long
    ): DepositoReciclaje {
        val request = DepositRequestDto(
            userId = idUsuario,
            stationId = stationId,
            bottlesCount = cantidadBotellas
        )
        
        val responseDto = api.registerDeposit(request)
        return responseDto.toDomain()
    }
}
