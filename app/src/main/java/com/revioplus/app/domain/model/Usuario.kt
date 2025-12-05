package com.revioplus.app.domain.model

import com.revioplus.app.domain.model.EstadoCuentaUsuario.ACTIVA
import com.revioplus.app.domain.model.EstadoCuentaUsuario.ELIMINADA
import com.revioplus.app.domain.model.EstadoCuentaUsuario.PENDIENTE_VERIFICACION
import com.revioplus.app.domain.model.EstadoCuentaUsuario.SUSPENDIDA
import com.revioplus.app.domain.model.TipoUsuario.ADMIN
import com.revioplus.app.domain.model.TipoUsuario.NORMAL
import com.revioplus.app.domain.model.TipoUsuario.OPERADOR

data class Usuario(
    val idUsuario: Long,
    val nombreMostrar: String,
    val nombres: String? = null,
    val apellidos: String? = null,
    val email: String,
    val telefono: String? = null,
    val ciudad: String? = null,
    val departamento: String? = null,
    val pais: String = "Perú",
    val avatarUrl: String? = null,

    val fechaRegistroMillis: Long, // epoch millis
    val estadoCuenta: EstadoCuentaUsuario = EstadoCuentaUsuario.ACTIVA,
    val tipoUsuario: TipoUsuario = TipoUsuario.NORMAL,

    val xpTotal: Long = 0L,
    val xpSiguienteNivel: Long = 0L,
    val nivelActual: Int = 1,
    val tituloNivel: String = "Nuevo Eco-Héroe",

    val rachaActualDias: Int = 0,
    val rachaMaximaDias: Int = 0,
    val fechaUltimoDepositoMillis: Long? = null,

    val totalBotellasRecicladas: Long = 0L,
    val totalCo2AhorradoKg: Double = 0.0,

    val tieneNfcHabilitado: Boolean = false,
    val miembroDesde: String, // ej. "15/11/2025"

    val aceptaTerminos: Boolean = false,
    val versionTerminosAceptados: String? = null,
    val fechaAceptacionTerminosMillis: Long? = null
)

enum class EstadoCuentaUsuario {
    ACTIVA, SUSPENDIDA, ELIMINADA, PENDIENTE_VERIFICACION

}

enum class TipoUsuario {
    NORMAL, ADMIN, OPERADOR;

}