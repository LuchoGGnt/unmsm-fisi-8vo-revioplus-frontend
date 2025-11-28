package com.revioplus.app.domain.repository

import com.revioplus.app.domain.model.Billetera
import kotlinx.coroutines.flow.Flow

interface WalletRepository {
    fun getWalletForUser(userId: Long): Flow<Billetera?>
}
