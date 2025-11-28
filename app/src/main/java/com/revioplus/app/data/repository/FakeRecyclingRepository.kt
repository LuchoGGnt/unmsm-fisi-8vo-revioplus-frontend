package com.revioplus.app.data.repository

import com.revioplus.app.domain.model.DepositoReciclaje
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.RecyclingStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class FakeRecyclingRepository : RecyclingRepository {

    // Lista en memoria de depósitos (empieza con 3 de ejemplo)
    private val depositosFlow = MutableStateFlow(
        listOf(
            DepositoReciclaje(
                idDeposito = 1L,
                idUsuario = 1L,
                idPuntoReciclaje = null,
                fechaHoraMillis = System.currentTimeMillis() - 1000L * 60 * 60,
                cantidadBotellas = 8,
                pesoEstimadoKg = 1.2,
                xpGenerado = 80,
                co2AhorradoKg = 2.5,
                fechaCreacionMillis = System.currentTimeMillis() - 1000L * 60 * 60
            ),
            DepositoReciclaje(
                idDeposito = 2L,
                idUsuario = 1L,
                idPuntoReciclaje = null,
                fechaHoraMillis = System.currentTimeMillis() - 1000L * 60 * 60 * 5,
                cantidadBotellas = 5,
                pesoEstimadoKg = 0.8,
                xpGenerado = 50,
                co2AhorradoKg = 1.6,
                fechaCreacionMillis = System.currentTimeMillis() - 1000L * 60 * 60 * 5
            ),
            DepositoReciclaje(
                idDeposito = 3L,
                idUsuario = 1L,
                idPuntoReciclaje = null,
                fechaHoraMillis = System.currentTimeMillis() - 1000L * 60 * 60 * 24,
                cantidadBotellas = 10,
                pesoEstimadoKg = 1.5,
                xpGenerado = 100,
                co2AhorradoKg = 3.0,
                fechaCreacionMillis = System.currentTimeMillis() - 1000L * 60 * 60 * 24
            )
        )
    )

    private val statsFlow = MutableStateFlow(
        calculateStats(depositosFlow.value)
    )

    private var nextId: Long =
        depositosFlow.value.maxOfOrNull { it.idDeposito }?.plus(1) ?: 1L

    override fun getRecentDeposits(limit: Int): Flow<List<DepositoReciclaje>> =
        depositosFlow.map { list ->
            list.sortedByDescending { it.fechaHoraMillis }
                .take(limit)
        }

    override fun getRecyclingStats(): Flow<RecyclingStats> =
        statsFlow.asStateFlow()

    override suspend fun registerDeposit(
        idUsuario: Long,
        cantidadBotellas: Int
    ): DepositoReciclaje {
        val now = System.currentTimeMillis()

        // Regla simple: 10 XP por botella, 0.35 kg CO₂ por botella
        val xp = cantidadBotellas * 10L
        val co2 = cantidadBotellas * 0.35
        val pesoKg = cantidadBotellas * 0.15

        val newDeposit = DepositoReciclaje(
            idDeposito = nextId++,
            idUsuario = idUsuario,
            idPuntoReciclaje = null,
            fechaHoraMillis = now,
            cantidadBotellas = cantidadBotellas,
            pesoEstimadoKg = pesoKg,
            xpGenerado = xp,
            co2AhorradoKg = co2,
            fechaCreacionMillis = now
        )

        val updatedList = listOf(newDeposit) + depositosFlow.value
        depositosFlow.value = updatedList
        statsFlow.value = calculateStats(updatedList)

        return newDeposit
    }

    private fun calculateStats(deposits: List<DepositoReciclaje>): RecyclingStats {
        val totalBotellas = deposits.sumOf { it.cantidadBotellas.toLong() }
        val totalCo2 = deposits.sumOf { it.co2AhorradoKg }
        return RecyclingStats(
            totalBotellasRecicladas = totalBotellas,
            totalCo2AhorradoKg = totalCo2
        )
    }
}