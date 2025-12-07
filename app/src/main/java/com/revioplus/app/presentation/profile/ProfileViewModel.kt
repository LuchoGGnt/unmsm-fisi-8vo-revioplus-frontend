package com.revioplus.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revioplus.app.domain.model.ProfileData
import com.revioplus.app.domain.usecase.GetProfileDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val data: ProfileData? = getProfileDataUseCase()
                if (data == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar la información de perfil (Datos nulos)."
                    )
                    return@launch
                }

                val user = data.usuario

                _uiState.value = ProfileUiState(
                    isLoading = false,
                    errorMessage = null,

                    displayName = user.nombreMostrar,
                    email = user.email,
                    phone = user.telefono.orEmpty(),
                    city = user.ciudad.orEmpty(),
                    department = user.departamento.orEmpty(),
                    country = user.pais,
                    memberSince = user.miembroDesde,

                    levelNumber = user.nivelActual,
                    levelTitle = user.tituloNivel,
                    xpTotal = user.xpTotal,

                    totalBottles = user.totalBotellasRecicladas,
                    totalCo2Kg = user.totalCo2AhorradoKg,

                    hasNfcEnabled = user.tieneNfcHabilitado
                )
            } catch (e: Exception) {
                // DEBUG: Mostrar el error real en la UI
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error: ${e.message ?: e.toString()}"
                )
            }
        }
    }
}
