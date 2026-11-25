package com.example.app_movil_tecnoventas.Modelo

import kotlinx.serialization.json.Json
import retrofit2.HttpException

object ErrorParser {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    fun obtenerMensaje(e: Throwable): String {
        if (e is HttpException) {
            val cuerpoError = e.response()?.errorBody()?.string()
            if (!cuerpoError.isNullOrBlank()) {
                try {
                    val apiError = json.decodeFromString<ApiResponse<String>>(cuerpoError)
                    if (apiError.message.isNotBlank()) {
                        return apiError.message
                    }
                } catch (_: Exception) {
                    return "Error del servidor (${e.code()})"
                }
            }
        }
        return e.localizedMessage ?: "Error de conexión con el servidor"
    }
}