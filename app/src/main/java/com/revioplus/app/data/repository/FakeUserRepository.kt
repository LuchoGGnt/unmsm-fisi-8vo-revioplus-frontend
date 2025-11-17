package com.revioplus.app.data.repository

import com.revioplus.app.domain.model.Usuario
import com.revioplus.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeUserRepository : UserRepository {
    // Un solo usuario fake en memoria
    private val currentUserFlow = MutableStateFlow(
        Usuario(
            idUsuario = 1L,
            nombreMostrar = "Alexo",
            nombres = "Alexander",
            apellidos = "Centeno Cerna",
            email = "alexander.centeno@unmsm.edu.pe",
            telefono = "999888777",
            ciudad = "Lima",
            departamento = "Lima",
            pais = "Perú",
            avatarUrl = null,
            fechaRegistroMillis = System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30,
            miembroDesde = "Oct 2025",
            xpTotal = 4200L,
            xpNivelActual = 200L,
            xpSiguienteNivel = 1000L,
            nivelActual = 3,
            tituloNivel = "Eco-Guerrero",
            rachaActualDias = 5,
            rachaMaximaDias = 12,
            totalBotellasRecicladas = 128,
            totalCo2AhorradoKg = 32.4,
            tieneNfcHabilitado = true,
            aceptaTerminos = true,
            versionTerminosAceptados = "1.0"
        )
    )

    override fun getCurrentUser(): Flow<Usuario?> = currentUserFlow.asStateFlow()
}