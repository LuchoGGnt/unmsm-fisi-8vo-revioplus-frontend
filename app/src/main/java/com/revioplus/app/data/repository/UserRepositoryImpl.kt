package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.local.SessionManager
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.toDomain
import com.revioplus.app.domain.model.Usuario
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ReVioApi,
    private val sessionManager: SessionManager // Inyectamos SessionManager
) : UserRepository {

    // Agregamos <Usuario?> explícitamente para permitir emit(null) en el catch
    override fun getCurrentUser(): Flow<Usuario?> = flow<Usuario?> {
        // Obtener el ID de la sesión actual
        val userId = sessionManager.userId.first()
        
        if (userId == null) {
            // Si no hay sesión, emitimos null (o podríamos lanzar error para forzar logout)
            Log.w("UserRepository", "No user logged in")
            emit(null)
            return@flow
        }
        
        // Llamada a la API con el ID real
        val userDto = api.getUserProfile(userId)
        
        // Mapeo y emisión
        emit(userDto.toDomain())
    }.catch { e ->
        Log.e("UserRepositoryImpl", "Error fetching user profile", e)
        emit(null)
    }
}
