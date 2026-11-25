package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistroRequest(
    @SerialName("nombre") val nombre: String = "",
    @SerialName("apellido_paterno") val apellidoPaterno: String = "",
    @SerialName("apellido_materno") val apellidoMaterno: String = "",
    @SerialName("correo") val correo: String = "",
    @SerialName("telefono") val telefono: String = "",
    @SerialName("password") val password: String = "",
    @SerialName("id_pregunta") val idPregunta: Int = 1,
    @SerialName("respuesta_seguridad") val respuestaSeguridad: String = ""
)