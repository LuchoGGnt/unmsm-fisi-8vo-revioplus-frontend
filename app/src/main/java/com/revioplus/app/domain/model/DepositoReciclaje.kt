package com.revioplus.app.domain.model

data class DepositoReciclaje(
    val idDeposito: Long,
    val idUsuario: Long,
    val idPuntoReciclaje: Long? = null,
    val fechaHoraMillis: Long,
    val cantidadBotellas: Int,
    val pesoEstimadoKg: Double? = null,
    val xpGenerado: Long,
    val co2AhorradoKg: Double,
    val origenRegistro: OrigenDeposito = OrigenDeposito.MANUAL,
    val estadoDeposito: EstadoDeposito = EstadoDeposito.REGISTRADO,
    val nota: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val fechaCreacionMillis: Long,
    val fechaActualizacionMillis: Long? = null
)

enum class OrigenDeposito { MANUAL, QR, NFC, OPERADOR }

enum class EstadoDeposito { REGISTRADO, VALIDADO, ANULADO }
