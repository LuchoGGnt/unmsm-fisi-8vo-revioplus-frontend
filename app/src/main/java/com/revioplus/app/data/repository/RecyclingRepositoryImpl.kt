package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.RecyclingDepositRequestDto
import com.revioplus.app.data.remote.dto.toDomain
import com.revioplus.app.domain.model.DepositoReciclaje
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.RecyclingStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecyclingRepositoryImpl @Inject constructor(
    private val api: ReVioApi
) : RecyclingRepository {

    override fun getRecentDeposits(limit: Int, userId: Long): Flow<List<DepositoReciclaje>> = flow {
        val dtos = api.getRecyclingHistory(userId)
        val deposits = dtos.map { it.toDomain() }
            .sortedByDescending { it.fechaHoraMillis }
            .take(limit)
            
        emit(deposits)
    }.catch { e ->
        Log.e("RecyclingRepo", "Error fetching deposits", e)
        emit(emptyList())
    }

    override fun getRecyclingStats(): Flow<RecyclingStats> = flow {
        // TODO: Implementar lógica para Estadísticas MENSUALES.
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
        // Construimos el objeto para enviar al backend
        val request = RecyclingDepositRequestDto(
            userId = idUsuario,
            stationId = stationId,
            bottlesCount = cantidadBotellas
        )
        
        // Llamada POST a la API
        val responseDto = api.registerDeposit(request)
        
        // Convertimos la respuesta a Dominio y la devolvemos
        return responseDto.toDomain()
    }
}
