package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.toDomain
import com.revioplus.app.domain.model.Usuario
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ReVioApi
) : UserRepository {

    override fun getCurrentUser(): Flow<Usuario?> = flow <Usuario?> {
        // TODO: Cuando tengamos Login, obtendremos este ID de las preferencias guardadas (DataStore)
        val userId = 1L 
        
        // Llamada a la API con el ID específico
        val userDto = api.getUserProfile(userId)
        
        // Mapeo y emisión
        emit(userDto.toDomain())
    }.catch { e ->
        // Este bloque solo se ejecuta si ocurre un error UPSTREAM (en la llamada a la API)
        // y respeta la cancelación si el usuario deja la pantalla o si usamos .first()
        Log.e("UserRepositoryImpl", "Error fetching user profile for ID 1", e)
        emit(null)
    }
}
