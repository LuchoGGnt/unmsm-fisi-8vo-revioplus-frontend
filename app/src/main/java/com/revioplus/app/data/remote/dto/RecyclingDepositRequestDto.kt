package com.revioplus.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecyclingDepositRequestDto(
    @SerializedName("userId") val userId: Long,
    @SerializedName("stationId") val stationId: Long,
    @SerializedName("bottlesCount") val bottlesCount: Int
)
