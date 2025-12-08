package com.revioplus.app.domain.usecase

import com.revioplus.app.data.local.SessionManager
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.LoginRequestDto
import javax.inject.Inject

/**
 * Caso de uso para manejar el inicio de sesión del usuario.
 * Devuelve true si el login fue exitoso, false en caso contrario.
 */
class LoginUseCase @Inject constructor(
    private val api: ReVioApi,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return try {
            // 1. Crear el objeto de petición
            val request = LoginRequestDto(email, password)
            
            // 2. Llamar a la API
            val response = api.login(request)
            
            // 3. Si la API no lanza excepción, guardamos la sesión
            sessionManager.saveUserId(response.id)
            
            // 4. Devolvemos true para indicar éxito
            true
        } catch (e: Exception) {
            // Si algo falla (401 Unauthorized, no hay internet, etc.),
            // la API lanzará una excepción.
            e.printStackTrace()
            false
        }
    }
}
