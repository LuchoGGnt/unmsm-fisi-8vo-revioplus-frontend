package com.revioplus.app.presentation.deposit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revioplus.app.domain.usecase.RegisterDepositUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val registerDepositUseCase: RegisterDepositUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DepositUiState())
    val uiState: StateFlow<DepositUiState> = _uiState

    fun onIncrement() {
        val current = _uiState.value.cantidadBotellas
        if (current < 51) { // límite
            _uiState.value = _uiState.value.copy(
                cantidadBotellas = current + 1,
                successMessage = null,
                errorMessage = null
            )
        }
    }

    fun onDecrement() {
        val current = _uiState.value.cantidadBotellas
        if (current > 1) {
            _uiState.value = _uiState.value.copy(
                cantidadBotellas = current - 1,
                successMessage = null,
                errorMessage = null
            )
        }
    }

    fun onRegisterDeposit() {
        val cantidad = _uiState.value.cantidadBotellas

        if (cantidad <= 0) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "La cantidad debe ser mayor que cero botellas"
            )
            return
        }

        if (cantidad > 50) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "La cantidad debe ser menor a 50 botellas"
            )
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isSaving = true,
                    errorMessage = null,
                    successMessage = null
                )

                // TODO: En el futuro, obtener el stationId desde el escáner QR.
                // Por ahora, simulamos que estamos en la Estación Central (ID 1)
                val simulatedStationId = 1L
                
                val deposit = registerDepositUseCase(cantidad, simulatedStationId)

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    successMessage = """Has registrado $cantidad botellas de reciclaje.
                            ${deposit.xpGenerado} XP 
                            ${deposit.co2AhorradoKg.toString().format("%.2f")}  kg CO₂""",
                    xpGanado = deposit.xpGenerado,
                    co2Ahorrado = deposit.co2AhorradoKg
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = "Error: ${e.message ?: "No se pudo registrar"}"
                )
            }
        }
    }
}
