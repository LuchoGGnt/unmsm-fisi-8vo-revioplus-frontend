package com.revioplus.app.domain.model

import com.revioplus.app.domain.repository.ChallengeProgress
import com.revioplus.app.domain.repository.RecyclingStats

data class HomeDashboard(
    val usuario: Usuario,
    val recyclingStats: RecyclingStats,
    val desafioActual: Desafio?,
    val progresoDesafio: ChallengeProgress?,
    val depositosRecientes: List<DepositoReciclaje>
)