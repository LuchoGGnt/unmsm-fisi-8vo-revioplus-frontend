package com.revioplus.app.data.remote

import com.revioplus.app.data.remote.dto.ChallengeDto
import com.revioplus.app.data.remote.dto.DepositRequestDto
import com.revioplus.app.data.remote.dto.LoginRequestDto
import com.revioplus.app.data.remote.dto.LoginResponseDto
import com.revioplus.app.data.remote.dto.RecyclingDepositDto
import com.revioplus.app.data.remote.dto.UpdateChallengeProgressRequestDto
import com.revioplus.app.data.remote.dto.UserDto
import com.revioplus.app.data.remote.dto.WalletDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReVioApi {

    // Auth
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    // User
    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") userId: Long): UserDto

    // Wallet
    @GET("users/{id}/wallet")
    suspend fun getUserWallet(@Path("id") userId: Long): WalletDto

    // Deposits
    @GET("users/{id}/deposits")
    suspend fun getRecyclingHistory(@Path("id") userId: Long): List<RecyclingDepositDto>

    @POST("deposits")
    suspend fun registerDeposit(@Body request: DepositRequestDto): RecyclingDepositDto

    // Challenges
    @GET("challenges/active")
    suspend fun getActiveChallenge(@Query("userId") userId: Long): ChallengeDto

    @PUT("challenges/progress")
    suspend fun updateChallengeProgress(@Body request: UpdateChallengeProgressRequestDto)
}
