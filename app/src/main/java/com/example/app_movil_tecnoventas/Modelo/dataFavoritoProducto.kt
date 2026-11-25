package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataFavoritoProducto(
    @SerialName("vchNo_Serie") val noSerie: String = "",
    @SerialName("vchNombre") val nombre: String = "",
    @SerialName("floPrecioUnitario") val precioUnitario: Double = 0.0,
    @SerialName("intDescuento") val descuento: Int = 0,
    @SerialName("precio_final") val precioFinal: Double = 0.0,
    @SerialName("vchImagen") val imagen: String = "",
    @SerialName("marca") val marca: String = ""
)