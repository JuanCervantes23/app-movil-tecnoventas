package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class PerfilUpdateRequest(
    @SerialName("id") val id: Int,
    @SerialName("nombre") val nombre: String,
    @SerialName("apellidos") val apellidos: String,
    @SerialName("correo") val correo: String,
    @SerialName("telefono") val telefono: String,
    @SerialName("password") val password: String? = null
)
