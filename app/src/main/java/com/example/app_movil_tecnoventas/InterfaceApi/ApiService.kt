package com.example.app_movil_tecnoventas.InterfaceApi

import com.example.app_movil_tecnoventas.Modelo.ApiResponse
import com.example.app_movil_tecnoventas.Modelo.LoginRequest
import com.example.app_movil_tecnoventas.Modelo.PerfilUpdateRequest
import com.example.app_movil_tecnoventas.Modelo.RegistroRequest
import com.example.app_movil_tecnoventas.Modelo.dataCategoria
import com.example.app_movil_tecnoventas.Modelo.dataCliente
import com.example.app_movil_tecnoventas.Modelo.dataDetalleProducto
import com.example.app_movil_tecnoventas.Modelo.dataPedido
import com.example.app_movil_tecnoventas.Modelo.dataPreguntaSeguridad
import com.example.app_movil_tecnoventas.Modelo.dataProducto
import retrofit2.Response
import retrofit2.http.Body
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

    // --- PERFIL Y PEDIDOS ---
    @GET("cliente_perfil.php")
    suspend fun obtenerPerfil(
        @Query("id") idCliente: Int
    ): ApiResponse<dataCliente>

    @POST("cliente_actualizar.php")
    suspend fun actualizarPerfil(
        @Body request: PerfilUpdateRequest
    ): ApiResponse<dataCliente>

    @GET("pedidos_listar.php")
    suspend fun obtenerPedidos(
        @Query("cliente_id") idCliente: Int
    ): ApiResponse<List<dataPedido>>

    // --- TIENDA, BÚSQUEDA Y DETALLE (Juan Francisco) ---
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