package com.example.app_movil_tecnoventas.Modelo

class clsPerfilModelo {

    suspend fun obtenerPerfil(idCliente: Int): dataCliente? {
        val respuesta = RetrofitCliente.apiService.obtenerPerfil(idCliente)
        return if (respuesta.status) respuesta.data else null
    }

    suspend fun actualizarPerfil(request: PerfilUpdateRequest): ApiResponse<dataCliente> {
        return RetrofitCliente.apiService.actualizarPerfil(request)
    }
}
