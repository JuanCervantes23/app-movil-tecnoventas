package com.example.app_movil_tecnoventas.Modelo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class clsTiendaModelo {

    private val api = RetrofitCliente.api

    suspend fun obtenerCategorias(): Result<List<dataCategoria>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.obtenerCategorias()
                val body = response.body()
                if (response.isSuccessful && body != null && body.status == "success" && body.data != null) {
                    Result.success(body.data)
                } else {
                    val mensaje = body?.message?.takeIf { it.isNotBlank() }
                        ?: ErrorParser.obtenerMensajeError(response)
                    Result.failure(Exception(mensaje))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error al cargar categorías: ${e.localizedMessage ?: "Sin conexión"}"))
            }
        }

    suspend fun obtenerProductos(
        categoriaId: Int? = null,
        busqueda: String? = null,
        destacados: Int? = null
    ): Result<List<dataProducto>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.obtenerProductos(categoriaId, busqueda, destacados)
                val body = response.body()
                if (response.isSuccessful && body != null && body.status == "success" && body.data != null) {
                    Result.success(body.data)
                } else {
                    val mensaje = body?.message?.takeIf { it.isNotBlank() }
                        ?: ErrorParser.obtenerMensajeError(response)
                    Result.failure(Exception(mensaje))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error al cargar productos: ${e.localizedMessage ?: "Sin conexión"}"))
            }
        }

    suspend fun obtenerDetalleProducto(productoId: Int): Result<dataDetalleProducto> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.obtenerDetalleProducto(productoId)
                val body = response.body()
                if (response.isSuccessful && body != null && body.status == "success" && body.data != null) {
                    Result.success(body.data)
                } else {
                    val mensaje = body?.message?.takeIf { it.isNotBlank() }
                        ?: ErrorParser.obtenerMensajeError(response)
                    Result.failure(Exception(mensaje))
                }
            } catch (e: Exception) {
                Result.failure(Exception("Error al cargar detalle: ${e.localizedMessage ?: "Sin conexión"}"))
            }
        }
}