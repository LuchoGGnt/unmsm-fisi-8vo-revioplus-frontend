package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.toDomainChallenge
import com.revioplus.app.data.remote.dto.toDomainProgress
import com.revioplus.app.domain.model.Desafio
import com.revioplus.app.domain.repository.ChallengeProgress
import com.revioplus.app.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val api: ReVioApi
) : ChallengeRepository {

    // CAMBIO: Agregamos <Desafio?> explícitamente para permitir emit(null)
    override fun getCurrentChallenge(): Flow<Desafio?> = flow<Desafio?> {
        val userId = 1L // Temporal hasta tener login
        val dto = api.getActiveChallenge(userId)
        emit(dto.toDomainChallenge())
    }.catch { e ->
        Log.e("ChallengeRepo", "Error fetching challenge", e)
        emit(null)
    }

    override fun getCurrentChallengeProgress(): Flow<ChallengeProgress> = flow {
        val userId = 1L
        val dto = api.getActiveChallenge(userId)
        emit(dto.toDomainProgress())
    }.catch { e ->
        Log.e("ChallengeRepo", "Error fetching progress", e)
        // Emitir progreso vacío en caso de error
        emit(ChallengeProgress(0, 100))
    }

    override suspend fun addProgress(botellas: Int) {
        // TODO: Implementar escritura
        // api.updateChallengeProgress(...)
    }
}
