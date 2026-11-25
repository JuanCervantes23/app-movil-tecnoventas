package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCliente(
    @SerialName("id") val id: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("apellido_paterno") val apellidoPaterno: String = "",
    @SerialName("apellido_materno") val apellidoMaterno: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("telefono") val telefono: String = "",
    @SerialName("direccion_completa") val direccionCompleta: String = "",
    @SerialName("calle") val calle: String = "",
    @SerialName("num_exterior") val numExterior: String = "",
    @SerialName("num_interior") val numInterior: String = "",
    @SerialName("colonia") val colonia: String = "",
    @SerialName("codigo_postal") val codigoPostal: String = "",
    @SerialName("ciudad") val ciudad: String = "",
    @SerialName("estado_rep") val estadoRep: String = "",
    @SerialName("referencias") val referencias: String = "",
    @SerialName("pedidos_activos") val pedidosActivos: Int = 0,
    @SerialName("favoritos_total") val favoritosTotal: Int = 0,
    @SerialName("favoritos") val favoritos: List<dataFavoritoProducto> = emptyList()
) {
    fun nombreCompleto(): String {
        return listOf(nombre, apellidoPaterno, apellidoMaterno)
            .filter { it.isNotBlank() }
            .joinToString(" ")
    }

    fun iniciales(): String {
        val p1 = nombre.firstOrNull()?.uppercaseChar() ?: 'C'
        val p2 = apellidoPaterno.firstOrNull()?.uppercaseChar() ?: 'T'
        return "$p1$p2"
    }
}