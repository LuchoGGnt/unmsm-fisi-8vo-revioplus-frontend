package com.revioplus.app.data.remote

import com.revioplus.app.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ReVioApi {

    // ANTES: "users/me"
    // AHORA: Recibe un ID dinámico
    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") userId: Long): UserDto
    
}
