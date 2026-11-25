package com.example.app_movil_tecnoventas.Modelo

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("tecnoventas_session", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }

    fun guardarCliente(cliente: dataCliente) {
        prefs.edit().putString("cliente_json", json.encodeToString(cliente)).apply()
    }

    fun obtenerCliente(): dataCliente? {
        val raw = prefs.getString("cliente_json", null) ?: return null
        return try {
            json.decodeFromString<dataCliente>(raw)
        } catch (e: Exception) {
            null
        }
    }

    fun haySesionActiva(): Boolean = obtenerCliente() != null

    fun cerrarSesion() {
        prefs.edit().remove("cliente_json").apply()
    }
}