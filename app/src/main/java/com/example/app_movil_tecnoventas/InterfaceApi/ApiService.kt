package com.example.app_movil_tecnoventas.InterfaceApi

import com.example.app_movil_tecnoventas.Modelo.ApiResponse
import com.example.app_movil_tecnoventas.Modelo.LoginRequest
import com.example.app_movil_tecnoventas.Modelo.RegistroRequest
import com.example.app_movil_tecnoventas.Modelo.dataCliente
import com.example.app_movil_tecnoventas.Modelo.dataPreguntaSeguridad
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Inicio de sesión (Oscar Abdiel)
    @POST("cliente_login.php")
    suspend fun loginCliente(
        @Body request: LoginRequest
    ): ApiResponse<dataCliente>

    // Preguntas de seguridad (Oscar Abdiel)
    @GET("cliente_registro.php")
    suspend fun obtenerPreguntasSeguridad(): ApiResponse<List<dataPreguntaSeguridad>>

    // Registro de cliente (Oscar Abdiel)
    @POST("cliente_registro.php")
    suspend fun registrarCliente(
        @Body request: RegistroRequest
    ): ApiResponse<dataCliente>
}