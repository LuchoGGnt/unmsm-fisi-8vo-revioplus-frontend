package com.revioplus.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    // El token que el backend nos devuelve
    @SerializedName("token") val token: String
)
