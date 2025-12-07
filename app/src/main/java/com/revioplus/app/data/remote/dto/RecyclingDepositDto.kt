package com.revioplus.app.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.revioplus.app.domain.model.DepositoReciclaje

data class RecyclingDepositDto(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("stationId") val stationId: Long?,
    @SerializedName("stationName") val stationName: String?, 
    @SerializedName("bottlesCount") val bottlesCount: Int,
    @SerializedName("xpGenerated") val xpGenerated: Long,
    @SerializedName("co2SavedKg") val co2SavedKg: Double,
    @SerializedName("createdAt") val createdAt: Long
)

fun RecyclingDepositDto.toDomain(): DepositoReciclaje {
    return DepositoReciclaje(
        idDeposito = id,
        idUsuario = userId,
        idPuntoReciclaje = stationId,
        nombrePuntoReciclaje = stationName,
        fechaHoraMillis = createdAt,
        cantidadBotellas = bottlesCount,
        xpGenerado = xpGenerated,
        co2AhorradoKg = co2SavedKg,
        fechaCreacionMillis = createdAt
    )
}
