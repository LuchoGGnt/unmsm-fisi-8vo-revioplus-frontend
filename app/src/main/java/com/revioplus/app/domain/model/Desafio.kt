package com.revioplus.app.domain.model

data class Desafio(
    val idDesafio: Long,
    val nombre: String,
    val descripcionCorta: String,
    val descripcionLarga: String? = null,
    val tipoDesafio: TipoDesafio = TipoDesafio.CANTIDAD_BOTELLAS,
    val alcance: AlcanceDesafio = AlcanceDesafio.GLOBAL,
    val fechaInicioMillis: Long,
    val fechaFinMillis: Long,
    val metaCantidadBotellas: Int? = null,
    val metaCo2Kg: Double? = null,
    val metaDiasRacha: Int? = null,
    val recompensaXp: Long,
    val recompensaDescripcion: String? = null,
    val estaActivo: Boolean = true,
    val ordenPrioridad: Int = 0
)

enum class TipoDesafio { CANTIDAD_BOTELLAS, CO2_AHORRADO, DIAS_RACHA }

enum class AlcanceDesafio { GLOBAL, POR_CIUDAD, POR_PAIS }