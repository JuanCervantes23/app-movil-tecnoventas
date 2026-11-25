package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataMetodoPago(
    @SerialName("id_metodo_pago") val idMetodoPago: Int = 0,
    @SerialName("id") val id: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("descripcion") val descripcion: String = ""
)
