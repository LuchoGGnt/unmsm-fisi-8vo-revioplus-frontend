package com.revioplus.app.domain.model

import java.math.BigDecimal

data class Transaccion(
    val idTransaccion: Long,
    val idBilletera: Long,
    val tipoTransaccion: TipoTransaccion,
    val origen: OrigenTransaccion,
    val idReferenciaOrigen: Long? = null,
    val monto: BigDecimal,
    val moneda: String = "PEN",
    val saldoAntes: BigDecimal? = null,
    val saldoDespues: BigDecimal? = null,
    val descripcion: String? = null,
    val estadoTransaccion: EstadoTransaccion = EstadoTransaccion.COMPLETADA,
    val fechaCreacionMillis: Long,
    val fechaActualizacionMillis: Long? = null,
    val canal: CanalTransaccion = CanalTransaccion.APP,
    val metodoRetiro: MetodoRetiro? = null,
    val datosDestinoResumidos: String? = null
)

enum class TipoTransaccion { INGRESO, RETIRO, AJUSTE, REVERSO }

enum class OrigenTransaccion {
    RECICLAJE, DESAFIO, ADMIN, PROMOCION, OTRO
}

enum class EstadoTransaccion { PENDIENTE, COMPLETADA, RECHAZADA, CANCELADA }

enum class CanalTransaccion { APP, WEB_ADMIN, SISTEMA }

enum class MetodoRetiro { TRANSFERENCIA_BANCARIA, YAPE, PLIN }