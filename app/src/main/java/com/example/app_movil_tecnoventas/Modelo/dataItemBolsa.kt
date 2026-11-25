package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataItemBolsa(
    @SerialName("no_serie") val noSerie: String = "",
    @SerialName("nombre") val nombre: String = "",
    @SerialName("marca") val marca: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("stock") val stock: Int = 0,
    @SerialName("cantidad") var cantidad: Int = 1,
    @SerialName("imagen") val imagen: String = ""
) {
    fun subtotal(): Double = precio * cantidad
}
