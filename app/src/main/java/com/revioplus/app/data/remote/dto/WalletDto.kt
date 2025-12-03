package com.revioplus.app.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.revioplus.app.domain.model.Billetera
import com.revioplus.app.domain.model.EstadoBilletera
import java.math.BigDecimal

/**
 * Representación JSON de la Billetera recibida del Backend.
 */
data class WalletDto(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("availableBalance") val availableBalance: BigDecimal,
    @SerializedName("lockedBalance") val lockedBalance: BigDecimal?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("createdAt") val createdAt: Long?,
    @SerializedName("lastMovementAt") val lastMovementAt: Long?,
    @SerializedName("dailyWithdrawalLimit") val dailyWithdrawalLimit: BigDecimal?,
    @SerializedName("minWithdrawalAmount") val minWithdrawalAmount: BigDecimal?
)

fun WalletDto.toDomain(): Billetera {
    val estado = when (status?.uppercase()) {
        "ACTIVE" -> EstadoBilletera.ACTIVA
        "LOCKED" -> EstadoBilletera.BLOQUEADA
        "CLOSED" -> EstadoBilletera.CERRADA
        else -> EstadoBilletera.ACTIVA
    }

    return Billetera(
        idBilletera = id,
        idUsuario = userId,
        saldoDisponible = availableBalance,
        saldoBloqueado = lockedBalance ?: BigDecimal.ZERO,
        moneda = currency ?: "PEN",
        estadoBilletera = estado,
        fechaCreacionMillis = createdAt ?: System.currentTimeMillis(),
        fechaUltimoMovimientoMillis = lastMovementAt,
        limiteRetiroDiario = dailyWithdrawalLimit,
        montoMinimoRetiro = minWithdrawalAmount
    )
}
