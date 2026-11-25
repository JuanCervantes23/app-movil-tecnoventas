package com.example.app_movil_tecnoventas.Modelo

class clsAccesoModelo {
    suspend fun iniciarSesion(correo: String, password: String): ApiResponse<dataCliente> {
        val request = LoginRequest(correo = correo, password = password)
        return RetrofitCliente.apiService.loginCliente(request)
    }

    suspend fun obtenerPreguntasSeguridad(): List<dataPreguntaSeguridad> {
        val respuesta = RetrofitCliente.apiService.obtenerPreguntasSeguridad()
        return if (respuesta.status && respuesta.data != null) {
            respuesta.data
        } else {
            emptyList()
        }
    }

    suspend fun registrarCliente(
        nombre: String,
        apPaterno: String,
        apMaterno: String,
        correo: String,
        telefono: String,
        password: String,
        idPregunta: Int,
        respuestaSeguridad: String
    ): ApiResponse<dataCliente> {
        val request = RegistroRequest(
            nombre = nombre,
            apellidoPaterno = apPaterno,
            apellidoMaterno = apMaterno,
            correo = correo,
            telefono = telefono,
            password = password,
            idPregunta = idPregunta,
            respuestaSeguridad = respuestaSeguridad
        )
        return RetrofitCliente.apiService.registrarCliente(request)
    }
}