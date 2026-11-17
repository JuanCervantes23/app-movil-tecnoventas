package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @SerialName("status") val status: Boolean = false,
    @SerialName("message") val message: String = "",
    @SerialName("data") val data: T? = null
)
