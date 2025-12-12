package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.local.SessionManager
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.UpdateChallengeProgressRequestDto
import com.revioplus.app.data.remote.dto.toDomainChallenge
import com.revioplus.app.data.remote.dto.toDomainProgress
import com.revioplus.app.domain.model.Desafio
import com.revioplus.app.domain.repository.ChallengeProgress
import com.revioplus.app.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val api: ReVioApi,
    private val sessionManager: SessionManager
) : ChallengeRepository {

    override fun getCurrentChallenge(): Flow<Desafio?> = flow<Desafio?> {

        val userId = sessionManager.userId.first() ?: return@flow emit(null)
        
        val dto = api.getActiveChallenge(userId)
        emit(dto.toDomainChallenge())
    }.catch { e ->
        Log.e("ChallengeRepo", "Error fetching challenge", e)
        emit(null)
    }

    override fun getCurrentChallengeProgress(): Flow<ChallengeProgress> = flow {

        val userId = sessionManager.userId.first()
            ?: return@flow emit(ChallengeProgress(0,100))

        val dto = api.getActiveChallenge(userId)

        emit(dto.toDomainProgress())
    }.catch { e ->
        Log.e("ChallengeRepo", "Error fetching progress", e)
        emit(ChallengeProgress(0, 100))
    }

    override suspend fun addProgress(userId : Long, bottles: Int) {

        val currentUserId = sessionManager.userId.first()

        if (currentUserId == null || userId != currentUserId) {
            Log.w("ChallengeRepo", "Sesión inválida")
            return
        }

        api.updateChallengeProgress(
            UpdateChallengeProgressRequestDto(userId, bottles)
        )
    }
}
