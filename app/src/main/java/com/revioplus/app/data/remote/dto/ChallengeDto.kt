package com.revioplus.app.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.revioplus.app.domain.model.Desafio
import com.revioplus.app.domain.repository.ChallengeProgress

data class ChallengeDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String, 
    @SerializedName("shortDescription") val shortDescription: String,
    @SerializedName("longDescription") val longDescription: String?,
    @SerializedName("startDate") val startDate: Long,
    @SerializedName("endDate") val endDate: Long,
    @SerializedName("targetBottles") val targetBottles: Int,
    @SerializedName("rewardXp") val rewardXp: Long,
    
    // Progreso del usuario en este desafío
    @SerializedName("currentProgressBottles") val currentProgressBottles: Int
)

fun ChallengeDto.toDomainChallenge(): Desafio {
    return Desafio(
        idDesafio = id,
        nombre = title,
        descripcionCorta = shortDescription,
        descripcionLarga = longDescription,
        fechaInicioMillis = startDate,
        fechaFinMillis = endDate,
        metaCantidadBotellas = targetBottles,
        recompensaXp = rewardXp
    )
}

fun ChallengeDto.toDomainProgress(): ChallengeProgress {
    return ChallengeProgress(
        botellasActuales = currentProgressBottles,
        metaBotellas = targetBottles
    )
}
