package com.revioplus.app.domain.model

import java.math.BigDecimal

data class Billetera(
    val idBilletera: Long,
    val idUsuario: Long,
    val saldoDisponible: BigDecimal,
    val saldoBloqueado: BigDecimal = BigDecimal.ZERO,
    val moneda: String = "PEN",
    val estadoBilletera: EstadoBilletera = EstadoBilletera.ACTIVA,
    val fechaCreacionMillis: Long,
    val fechaUltimoMovimientoMillis: Long? = null,
    val limiteRetiroDiario: BigDecimal? = null,
    val montoMinimoRetiro: BigDecimal? = null
)

enum class EstadoBilletera {
    ACTIVA, BLOQUEADA, CERRADA
}