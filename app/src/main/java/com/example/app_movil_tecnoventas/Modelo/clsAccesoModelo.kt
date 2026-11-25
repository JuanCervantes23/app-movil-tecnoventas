package com.example.app_movil_tecnoventas.Modelo

class clsAccesoModelo {

    suspend fun iniciarSesion(correo: String, password: String): Result<dataCliente> {
        return try {
            val body = RetrofitCliente.apiService.loginCliente(LoginRequest(correo, password))
            if (body.status && body.data != null) {
                Result.success(body.data)
            } else {
                Result.failure(Exception(body.message.ifBlank { "Credenciales incorrectas" }))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.localizedMessage ?: "Sin conexión"}"))
        }
    }

    suspend fun obtenerPreguntasSeguridad(): Result<List<dataPreguntaSeguridad>> {
        return try {
            val body = RetrofitCliente.apiService.obtenerPreguntasSeguridad()
            if (body.status && body.data != null) {
                Result.success(body.data)
            } else {
                Result.failure(Exception(body.message.ifBlank { "No se pudieron cargar las preguntas" }))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.localizedMessage ?: "Sin conexión"}"))
        }
    }

    suspend fun registrarCliente(request: RegistroRequest): Result<dataCliente> {
        return try {
            val body = RetrofitCliente.apiService.registrarCliente(request)
            if (body.status && body.data != null) {
                Result.success(body.data)
            } else {
                Result.failure(Exception(body.message.ifBlank { "No se pudo completar el registro" }))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.localizedMessage ?: "Sin conexión"}"))
        }
    }
}