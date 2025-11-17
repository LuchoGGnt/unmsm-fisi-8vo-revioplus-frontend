package com.revioplus.app.domain.model

data class ConfiguracionUsuario(
    val idUsuario: Long,
    val idioma: String = "es-PE",
    val zonaHoraria: String? = null,
    val recibirNotificacionesPush: Boolean = true,
    val recibirNotificacionesEmail: Boolean = false,
    val recibirResumenMensual: Boolean = false,
    val recordatorioReciclajeActivo: Boolean = false,
    val horaRecordatorioReciclaje: String? = null, // "20:00"
    val temaVisual: TemaVisual = TemaVisual.SISTEMA,
    val compartirEstadisticasPublicas: Boolean = false,
    val metodoRetiroPreferido: MetodoRetiro? = null,
    val fechaUltimaActualizacionMillis: Long? = null
)

enum class TemaVisual { SISTEMA, CLARO, OSCURO }