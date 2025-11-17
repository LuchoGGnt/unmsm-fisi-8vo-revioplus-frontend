package com.revioplus.app.domain.model

data class PuntoReciclaje(
    val idPuntoReciclaje: Long,
    val nombre: String,
    val descripcion: String? = null,
    val tipoPunto: TipoPuntoReciclaje = TipoPuntoReciclaje.CONTENEDOR,
    val direccionLinea1: String? = null,
    val direccionLinea2: String? = null,
    val distrito: String? = null,
    val ciudad: String? = null,
    val departamento: String? = null,
    val pais: String = "Perú",
    val latitud: Double? = null,
    val longitud: Double? = null,
    val horarioApertura: String? = null, // ej. "08:00"
    val horarioCierre: String? = null,   // ej. "22:00"
    val diasOperacion: String? = null,   // ej. "Lun-Dom"
    val estado: EstadoPuntoReciclaje = EstadoPuntoReciclaje.ACTIVO,
    val codigoQr: String? = null,
    val soportaNfc: Boolean = false,
    val capacidadMaxBotellas: Int? = null,
    val capacidadActualBotellas: Int? = null,
    val fechaCreacionMillis: Long,
    val fechaActualizacionMillis: Long? = null
)

enum class TipoPuntoReciclaje { CONTENEDOR, TIENDA, EVENTO_TEMPORAL, OTRO }

enum class EstadoPuntoReciclaje { ACTIVO, INACTIVO, EN_MANTENIMIENTO }