package com.revioplus.app.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.revioplus.app.domain.model.EstadoCuentaUsuario
import com.revioplus.app.domain.model.EstadoCuentaUsuario.ACTIVA
import com.revioplus.app.domain.model.EstadoCuentaUsuario.ELIMINADA
import com.revioplus.app.domain.model.EstadoCuentaUsuario.PENDIENTE_VERIFICACION
import com.revioplus.app.domain.model.EstadoCuentaUsuario.SUSPENDIDA
import com.revioplus.app.domain.model.TipoUsuario
import com.revioplus.app.domain.model.TipoUsuario.ADMIN
import com.revioplus.app.domain.model.TipoUsuario.NORMAL
import com.revioplus.app.domain.model.TipoUsuario.OPERADOR
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
    @SerializedName("nfcEnabled") val nfcEnabled: Boolean?,

    // Campos faltantes para mapear con la Entidad en Frontend
    @SerializedName("nextLevelXp") val nextLevelXp: Long,
    @SerializedName("lastDepositDateMillis") val lastDepositDateMillis: Long?,
    @SerializedName("termsAccepted") val termsAccepted: Boolean,
    @SerializedName("acceptedTermsVersion") val acceptedTermsVersion: String?,
    @SerializedName("termsAcceptedDateMillis") val termsAcceptedDateMillis: Long?,
    @SerializedName("userType") val userType: String,
    @SerializedName("accountStatus") val accountStatus: String
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

        xpSiguienteNivel = nextLevelXp,
        fechaUltimoDepositoMillis =  lastDepositDateMillis,
        aceptaTerminos =  termsAccepted,
        versionTerminosAceptados =  acceptedTermsVersion,
        fechaAceptacionTerminosMillis = termsAcceptedDateMillis,
        tipoUsuario = identificarTipo(userType),
        estadoCuenta = identificarEstado(accountStatus),
    )
}


fun UserDto.identificarEstado(estado : String ) =
    when (estado){
        "ACTIVA" ->ACTIVA
        "SUSPENDIDA" -> SUSPENDIDA
        "ELIMINADA" ->  ELIMINADA
        "PENDIENTE_VERIFICACION" ->  PENDIENTE_VERIFICACION
        else ->  ACTIVA
    }

fun UserDto.identificarTipo(tipo : String ) =
    when (tipo){
        "NORMAL" ->NORMAL
        "ADMIN" -> ADMIN
        "OPERADOR" -> OPERADOR
        else -> NORMAL
    }
