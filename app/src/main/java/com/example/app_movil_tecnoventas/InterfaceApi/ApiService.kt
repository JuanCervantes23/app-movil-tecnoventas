package com.example.app_movil_tecnoventas.InterfaceApi

import com.example.app_movil_tecnoventas.Modelo.ApiResponse
import com.example.app_movil_tecnoventas.Modelo.LoginRequest
import com.example.app_movil_tecnoventas.Modelo.RegistroRequest
import com.example.app_movil_tecnoventas.Modelo.dataCategoria
import com.example.app_movil_tecnoventas.Modelo.dataCliente
import com.example.app_movil_tecnoventas.Modelo.dataDetalleProducto
import com.example.app_movil_tecnoventas.Modelo.dataPreguntaSeguridad
import com.example.app_movil_tecnoventas.Modelo.dataProducto
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("categorias_listar.php")
    suspend fun obtenerCategorias(): Response<ApiResponse<List<dataCategoria>>>

    @GET("productos_listar.php")
    suspend fun obtenerProductos(
        @Query("categoria_id") categoriaId: Int? = null,
        @Query("busqueda") busqueda: String? = null,
        @Query("destacados") destacados: Int? = null
    ): Response<ApiResponse<List<dataProducto>>>

    @GET("producto_detalle.php")
    suspend fun obtenerDetalleProducto(
        @Query("id") productoId: Int
    ): Response<ApiResponse<dataDetalleProducto>>
}