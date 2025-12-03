package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.remote.ReVioApi
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
        // La vista para esto aún no está desarrollada.
        // Las estadísticas globales (de todos los tiempos) se obtienen directamente del Usuario,
        // por lo que este método se reservará para filtros de fechas o reportes mensuales.
        
        // Por ahora emitimos valores en cero para no romper la compilación ni hacer llamadas incorrectas.
        emit(RecyclingStats(0, 0.0))
        
    }.catch { e ->
         Log.e("RecyclingRepo", "Error fetching stats", e)
         emit(RecyclingStats(0, 0.0))
    }

    override suspend fun registerDeposit(
        idUsuario: Long,
        cantidadBotellas: Int
    ): DepositoReciclaje {
        // TODO: Implementar POST al backend. 
        // Por ahora lanzamos error para indicar que falta la integración de escritura.
        throw NotImplementedError("La escritura en backend (POST) aún no está implementada.")
    }
}
