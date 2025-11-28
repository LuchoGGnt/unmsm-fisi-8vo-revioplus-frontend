package com.revioplus.app.presentation.profile.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revioplus.app.domain.model.Billetera
import com.revioplus.app.domain.model.EstadoBilletera
import com.revioplus.app.domain.usecase.GetWalletForCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getWalletForCurrentUserUseCase: GetWalletForCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState

    init {
        loadWallet()
    }

    fun loadWallet() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val wallet: Billetera? = getWalletForCurrentUserUseCase()
                if (wallet == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se encontró información de tu billetera."
                    )
                    return@launch
                }

                val moneda = wallet.moneda
                val saldoDisponible = formatMoney(wallet.saldoDisponible, moneda)
                val saldoBloqueado = formatMoney(wallet.saldoBloqueado, moneda)
                val limiteRetiroDiario = wallet.limiteRetiroDiario?.let {
                    formatMoney(it, moneda)
                } ?: "Sin límite configurado"
                val montoMinimoRetiro = wallet.montoMinimoRetiro?.let {
                    formatMoney(it, moneda)
                } ?: "No definido"

                val ultimaActualizacion = wallet.fechaUltimoMovimientoMillis?.let {
                    formatDate(it)
                } ?: "Sin movimientos recientes"

                val estadoText = when (wallet.estadoBilletera) {
                    EstadoBilletera.ACTIVA -> "Activa"
                    EstadoBilletera.BLOQUEADA -> "Bloqueada"
                    EstadoBilletera.CERRADA -> "Cerrada"
                }

                _uiState.value = WalletUiState(
                    isLoading = false,
                    errorMessage = null,
                    saldoDisponibleText = saldoDisponible,
                    saldoBloqueadoText = saldoBloqueado,
                    moneda = moneda,
                    limiteRetiroDiarioText = limiteRetiroDiario,
                    montoMinimoRetiroText = montoMinimoRetiro,
                    ultimaActualizacionText = ultimaActualizacion,
                    estadoText = estadoText
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Ocurrió un error al cargar tu billetera."
                )
            }
        }
    }

    private fun formatMoney(amount: BigDecimal, currency: String): String {
        val formatted = amount.setScale(2, RoundingMode.HALF_UP).toPlainString()
        return when (currency.uppercase(Locale.getDefault())) {
            "PEN" -> "S/ $formatted"
            else -> "$formatted $currency"
        }
    }

    private fun formatDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(millis))
    }
}
