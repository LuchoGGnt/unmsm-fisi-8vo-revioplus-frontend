package com.revioplus.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revioplus.app.domain.model.HomeDashboard
import com.revioplus.app.domain.usecase.GetHomeDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.map

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDashboardUseCase: GetHomeDashboardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val dashboard: HomeDashboard? = getHomeDashboardUseCase()
                if (dashboard == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar la información del usuario."
                    )
                    return@launch
                }

                val user = dashboard.usuario
                val stats = dashboard.recyclingStats
                val desafio = dashboard.desafioActual
                val progreso = dashboard.progresoDesafio

                val depositsUi = dashboard.depositosRecientes.map { deposito ->
                    val fecha = formatDate(deposito.fechaHoraMillis)
                    val bottles = deposito.cantidadBotellas
                    "$fecha · $bottles botellas recicladas"
                }

                _uiState.value = HomeUiState(
                    isLoading = false,
                    userName = user.nombreMostrar,
                    city = user.ciudad ?: "",
                    levelNumber = user.nivelActual,
                    levelTitle = user.tituloNivel,
                    xpCurrent = user.xpNivelActual,
                    xpNext = user.xpSiguienteNivel,
                    bottles = stats.totalBotellasRecicladas,
                    co2Kg = stats.totalCo2AhorradoKg,
                    challengeTitle = desafio?.nombre ?: "",
                    challengeProgress = progreso?.botellasActuales ?: 0,
                    challengeGoal = progreso?.metaBotellas ?: (desafio?.metaCantidadBotellas ?: 0),
                    recentDepositsText = depositsUi,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Ocurrió un error al cargar el panel de inicio."
                )
            }
        }
    }

    private fun formatDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
        return sdf.format(Date(millis))
    }
}