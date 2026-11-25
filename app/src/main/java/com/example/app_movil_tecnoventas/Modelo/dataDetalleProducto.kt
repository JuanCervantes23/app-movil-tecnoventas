package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataDetalleProducto(
    @SerialName("id") val id: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("descripcion") val descripcion: String = "",
    @SerialName("especificaciones") val especificaciones: String = "",
    @SerialName("precio") val precio: Double = 0.0,
    @SerialName("precio_anterior") val precioAnterior: Double? = null,
    @SerialName("stock") val stock: Int = 0,
    @SerialName("imagen") val imagen: String = "",
    @SerialName("galeria") val galeria: List<String> = emptyList(),
    @SerialName("categoria_id") val categoriaId: Int = 0,
    @SerialName("categoria") val categoria: String = "",
    @SerialName("marca") val marca: String = ""
)
