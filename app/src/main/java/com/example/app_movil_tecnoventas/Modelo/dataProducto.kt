package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataProducto(
    @SerialName("id") val id: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("precio_anterior") val precioAnterior: Double? = null,
    @SerialName("stock") val stock: Int = 0,
    @SerialName("imagen") val imagen: String = "",
    @SerialName("categoria") val categoria: String = "",
    @SerialName("marca") val marca: String = "",
    @SerialName("destacado") val destacado: Int = 0
)
