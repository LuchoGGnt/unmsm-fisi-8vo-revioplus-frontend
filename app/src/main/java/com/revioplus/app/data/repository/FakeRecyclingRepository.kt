package com.revioplus.app.data.repository

import com.revioplus.app.domain.model.DepositoReciclaje
import com.revioplus.app.domain.repository.RecyclingRepository
import com.revioplus.app.domain.repository.RecyclingStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeRecyclingRepository : RecyclingRepository {

    private val depositos = listOf(
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

    private val depositosFlow = MutableStateFlow(depositos)

    override fun getRecentDeposits(limit: Int): Flow<List<DepositoReciclaje>> =
        depositosFlow.asStateFlow()

    override fun getRecyclingStats(): Flow<RecyclingStats> {
        val totalBotellas = depositos.sumOf { it.cantidadBotellas.toLong() }
        val totalCo2 = depositos.sumOf { it.co2AhorradoKg }
        val stats = RecyclingStats(
            totalBotellasRecicladas = totalBotellas,
            totalCo2AhorradoKg = totalCo2
        )
        return MutableStateFlow(stats).asStateFlow()
    }
}
