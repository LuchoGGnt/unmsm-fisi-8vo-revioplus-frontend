package com.revioplus.app.data.repository

import com.revioplus.app.domain.model.Desafio
import com.revioplus.app.domain.model.TipoDesafio
import com.revioplus.app.domain.repository.ChallengeProgress
import com.revioplus.app.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeChallengeRepository : ChallengeRepository {

    private val currentChallenge = Desafio(
        idDesafio = 10L,
        nombre = "Reto de Verano",
        descripcionCorta = "Recicla 50 botellas este mes",
        tipoDesafio = TipoDesafio.CANTIDAD_BOTELLAS,
        fechaInicioMillis = System.currentTimeMillis(),
        fechaFinMillis = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30, // +30 días
        metaCantidadBotellas = 50,
        recompensaXp = 500,
        estaActivo = true
    )

    private val _progressFlow = MutableStateFlow(
        ChallengeProgress(
            botellasActuales = 15,
            metaBotellas = 50
        )
    )

    override fun getCurrentChallenge(): Flow<Desafio?> {
        return MutableStateFlow(currentChallenge).asStateFlow()
    }

    override fun getCurrentChallengeProgress(): Flow<ChallengeProgress> {
        return _progressFlow.asStateFlow()
    }

    // ACTUALIZADO: Ahora acepta userId para cumplir con la interfaz
    override suspend fun addProgress(userId: Long, bottles: Int) {
        val current = _progressFlow.value
        val newCount = (current.botellasActuales + bottles).coerceAtMost(current.metaBotellas)
        
        _progressFlow.value = current.copy(
            botellasActuales = newCount
        )
    }
}
