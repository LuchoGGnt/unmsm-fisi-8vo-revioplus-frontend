package com.revioplus.app.presentation.profile.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revioplus.app.domain.model.DepositoReciclaje
import com.revioplus.app.domain.usecase.GetHistoryForCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileHistoryViewModel @Inject constructor(
    private val getHistoryForCurrentUserUseCase : GetHistoryForCurrentUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileHistoryUiState())
    val uiState : StateFlow<ProfileHistoryUiState> = _uiState

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            // 1. Indicar que estamos cargando
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                // 2. Llamar al caso de uso (dentro de la corrutina)
                val deposits: List<DepositoReciclaje>? = getHistoryForCurrentUserUseCase()

                if (deposits == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar el historial (Usuario no encontrado)."
                    )
                    return@launch
                }

                // 3. Mapear los datos de Dominio a UI
                val uiItems = deposits.map { deposito ->
                    // Formatear fecha
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateStr = sdf.format(Date(deposito.fechaHoraMillis))

                    DepositHistoryItemUi(
                        id = deposito.idDeposito.toString(),
                        bottles = deposito.cantidadBotellas,
                        // Usa el nombre si existe, si no, usa el ID como fallback.
                        location = deposito.nombrePuntoReciclaje ?: "Punto #${deposito.idPuntoReciclaje ?: '?'}",
                        dateText = dateStr,
                        xpEarnedText = "+${deposito.xpGenerado} XP",
                        co2SavedText = "+${String.format("%.1f", deposito.co2AhorradoKg)} kg CO₂"
                    )
                }

                // 4. Actualizar el estado con los datos listos
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    recentDeposits = uiItems,
                    // Opcional: Calcular totales si la UI los pide
                    totalDepositsThisMonthText = "${uiItems.size}",
                    totalBottlesThisMonthText = "${uiItems.sumOf { it.bottles }}"
                )

            } catch (e: Exception) {
                // 5. Manejo de errores
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar historial: ${e.localizedMessage}"
                )
            }
        }
    }
}
