package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    @SerialName("vchNo_Serie") val noSerie: String,
    @SerialName("intid_Marca") val idMarca: Int = 0,
    @SerialName("marca") val marca: String = "",
    @SerialName("intid_Categoria") val idCategoria: Int = 0,
    @SerialName("categoria") val categoria: String = "",
    @SerialName("vchNombre") val nombre: String,
    @SerialName("vchDescripcion") val descripcion: String = "",
    @SerialName("floPrecioUnitario") val precioUnitario: Double,
    @SerialName("intStock") val stock: Int,
    @SerialName("vchImagen") val imagen: String = "",
    @SerialName("vchAudio_Url") val audioUrl: String? = null,
    @SerialName("vchVideo_Url") val videoUrl: String? = null
)
