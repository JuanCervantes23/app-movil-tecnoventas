package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class dataPedido(
    @SerialName("id") val id: Int = 0,
    @SerialName("folio") val folio: String = "",
    @SerialName("estado") val estado: String = "",
    @SerialName("fecha") val fecha: String = "",
    @SerialName("metodo_pago") val metodoPago: String = "",
    @SerialName("total_articulos") val totalArticulos: Int = 0,
    @SerialName("total") val total: Double = 0.0,
    @SerialName("items") val items: List<dataDetallePedido> = emptyList()
)
