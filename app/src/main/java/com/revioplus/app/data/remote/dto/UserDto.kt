package com.revioplus.app.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.revioplus.app.domain.model.Usuario

/**
 * Representación JSON del Usuario recibida del Backend.
 */
data class UserDto(
    @SerializedName("id") val id: Long,
    @SerializedName("username") val username: String, // nombreMostrar
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("department") val department: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("avatarUrl") val avatarUrl: String?,
    
    @SerializedName("registeredAt") val registeredAt: Long?, // timestamp millis
    @SerializedName("memberSince") val memberSince: String?, // Texto formateado ej: "Oct 2025"
    
    // Stats / Gamification
    @SerializedName("totalXp") val totalXp: Long?,
    @SerializedName("currentLevel") val currentLevel: Int?,
    @SerializedName("levelTitle") val levelTitle: String?,
    @SerializedName("currentStreak") val currentStreak: Int?,
    @SerializedName("maxStreak") val maxStreak: Int?,
    
    // Recycling Stats
    @SerializedName("totalBottles") val totalBottles: Long?,
    @SerializedName("totalCo2Saved") val totalCo2Saved: Double?,
    
    // Settings
    @SerializedName("nfcEnabled") val nfcEnabled: Boolean?
)

fun UserDto.toDomain(): Usuario {
    return Usuario(
        idUsuario = id,
        nombreMostrar = username,
        nombres = firstName,
        apellidos = lastName,
        email = email,
        telefono = phone,
        ciudad = city,
        departamento = department,
        pais = country ?: "Perú",
        avatarUrl = avatarUrl,
        fechaRegistroMillis = registeredAt ?: System.currentTimeMillis(),
        miembroDesde = memberSince ?: "",
        
        xpTotal = totalXp ?: 0L,
        nivelActual = currentLevel ?: 1,
        tituloNivel = levelTitle ?: "Nuevo",
        rachaActualDias = currentStreak ?: 0,
        rachaMaximaDias = maxStreak ?: 0,
        
        totalBotellasRecicladas = totalBottles ?: 0L,
        totalCo2AhorradoKg = totalCo2Saved ?: 0.0,
        
        tieneNfcHabilitado = nfcEnabled ?: false,
        
        // Valores por defecto para campos internos o no mapeados aun
        aceptaTerminos = true
    )
}
