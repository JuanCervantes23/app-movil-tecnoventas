package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataPreguntaSeguridad(
    @SerialName("intid_Pregunta") val idPregunta: Int = 0,
    @SerialName("vchPregunta") val pregunta: String = ""
) {
    override fun toString(): String = pregunta
}