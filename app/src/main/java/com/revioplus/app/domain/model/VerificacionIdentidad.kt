package com.revioplus.app.domain.model

data class VerificacionIdentidad(
    val idVerificacion: Long,
    val idUsuario: Long,
    val estado: EstadoVerificacion = EstadoVerificacion.NO_INICIADA,
    val tipoDocumento: TipoDocumento = TipoDocumento.DNI,
    val numeroDocumento: String,
    val nombresDocumento: String? = null,
    val apellidosDocumento: String? = null,
    val fechaNacimiento: String? = null, // "1990-05-21"
    val fechaSolicitudMillis: Long? = null,
    val fechaVerificacionMillis: Long? = null,
    val fechaActualizacionMillis: Long? = null,
    val nivelVerificacion: NivelVerificacion = NivelVerificacion.BASICO,
    val motivoRechazo: String? = null,
    val origenVerificacion: OrigenVerificacion = OrigenVerificacion.MANUAL
)

enum class EstadoVerificacion { NO_INICIADA, EN_PROCESO, VERIFICADA, RECHAZADA }

enum class TipoDocumento { DNI, CE, PASAPORTE }

enum class NivelVerificacion { BASICO, COMPLETO }

enum class OrigenVerificacion { MANUAL, SERVICIO_EXTERNO }