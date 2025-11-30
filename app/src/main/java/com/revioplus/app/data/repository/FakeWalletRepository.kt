package com.revioplus.app.data.repository

import com.revioplus.app.domain.model.Billetera
import com.revioplus.app.domain.model.EstadoBilletera
import com.revioplus.app.domain.repository.WalletRepository
import java.math.BigDecimal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeWalletRepository : WalletRepository {

    private val walletFlow = MutableStateFlow(
        Billetera(
            idBilletera = 1L,
            idUsuario = 1L,
            saldoDisponible = BigDecimal("45.50"),
            saldoBloqueado = BigDecimal("10.00"),
            moneda = "PEN",
            estadoBilletera = EstadoBilletera.ACTIVA,
            fechaCreacionMillis = System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 20,
            fechaUltimoMovimientoMillis = System.currentTimeMillis() - 1000L * 60 * 10,
            limiteRetiroDiario = BigDecimal("200.00"),
            montoMinimoRetiro = BigDecimal("10.00")
        )
    )

    override fun getWalletForUser(userId: Long): Flow<Billetera?> {
        // En este fake ignoramos userId y devolvemos siempre la misma billetera
        return walletFlow.asStateFlow()
    }
}
