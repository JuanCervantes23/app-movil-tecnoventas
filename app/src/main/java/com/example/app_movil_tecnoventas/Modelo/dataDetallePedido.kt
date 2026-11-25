package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataDetallePedido(
    @SerialName("producto_id") val productoId: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("cantidad") val cantidad: Int = 0,
    @SerialName("precio_unitario") val precioUnitario: Double = 0.0,
    @SerialName("subtotal") val subtotal: Double = 0.0
)
