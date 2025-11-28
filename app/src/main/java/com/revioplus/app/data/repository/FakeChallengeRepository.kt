package com.revioplus.app.data.repository

import com.revioplus.app.domain.model.Desafio
import com.revioplus.app.domain.repository.ChallengeProgress
import com.revioplus.app.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeChallengeRepository : ChallengeRepository {

    private val currentChallengeFlow = MutableStateFlow(
        Desafio(
            idDesafio = 1L,
            nombre = "Desafío semanal: recicla 50 botellas",
            descripcionCorta = "Completa la meta antes del domingo y gana XP extra.",
            descripcionLarga = "Recicla 50 botellas durante esta semana para obtener 200 XP adicionales.",
            fechaInicioMillis = System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 2,
            fechaFinMillis = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 5,
            metaCantidadBotellas = 50,
            recompensaXp = 200
        )
    )

    private val progressFlow = MutableStateFlow(
        ChallengeProgress(
            botellasActuales = 23,
            metaBotellas = 50
        )
    )

    override fun getCurrentChallenge(): Flow<Desafio?> = currentChallengeFlow.asStateFlow()

    override fun getCurrentChallengeProgress(): Flow<ChallengeProgress> =
        progressFlow.asStateFlow()

    override suspend fun addProgress(botellas: Int) {
        val current = progressFlow.value
        val updated = current.copy(
            botellasActuales = current.botellasActuales + botellas
        )
        progressFlow.value = updated
    }
}