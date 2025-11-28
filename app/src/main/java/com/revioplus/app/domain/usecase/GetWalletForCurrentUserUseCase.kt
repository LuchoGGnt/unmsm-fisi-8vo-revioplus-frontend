package com.revioplus.app.domain.usecase

import com.revioplus.app.domain.model.Billetera
import com.revioplus.app.domain.repository.UserRepository
import com.revioplus.app.domain.repository.WalletRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetWalletForCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(): Billetera? {
        val user = userRepository.getCurrentUser().first() ?: return null
        return walletRepository.getWalletForUser(user.idUsuario).first()
    }
}
