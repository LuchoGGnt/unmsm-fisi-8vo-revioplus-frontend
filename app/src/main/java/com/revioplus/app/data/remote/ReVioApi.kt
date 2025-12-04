package com.revioplus.app.data.remote

import com.revioplus.app.data.remote.dto.ChallengeDto
import com.revioplus.app.data.remote.dto.RecyclingDepositDto
import com.revioplus.app.data.remote.dto.UserDto
import com.revioplus.app.data.remote.dto.WalletDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReVioApi {

    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") userId: Long): UserDto

    @GET("users/{id}/wallet")
    suspend fun getUserWallet(@Path("id") userId: Long): WalletDto

    @GET("users/{id}/deposits")
    suspend fun getRecyclingHistory(@Path("id") userId: Long): List<RecyclingDepositDto>

    @GET("challenges/active")
    suspend fun getActiveChallenge(@Query("userId") userId: Long): ChallengeDto
    
}
