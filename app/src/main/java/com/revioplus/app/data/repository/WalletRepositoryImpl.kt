package com.revioplus.app.data.repository

import android.util.Log
import com.revioplus.app.data.remote.ReVioApi
import com.revioplus.app.data.remote.dto.toDomain
import com.revioplus.app.domain.model.Billetera
import com.revioplus.app.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val api: ReVioApi
) : WalletRepository {

    override fun getWalletForUser(userId: Long): Flow<Billetera?> = flow<Billetera?> {
        val walletDto = api.getUserWallet(userId)
        emit(walletDto.toDomain())
    }.catch { e ->
        Log.e("WalletRepositoryImpl", "Error fetching wallet for user $userId", e)
        emit(null)
    }
}
