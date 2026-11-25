package com.example.app_movil_tecnoventas.Modelo

class clsTiendaModelo {

    suspend fun obtenerCategorias(): Result<List<dataCategoria>> {
        return try {
            val response = RetrofitCliente.apiService.obtenerCategorias()
            val body = response.body()
            if (response.isSuccessful && body != null && body.status && body.data != null) {
                Result.success(body.data)
            } else {
                Result.failure(Exception(body?.message ?: "No se pudieron cargar las categorías"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.localizedMessage ?: "Sin conexión"}"))
        }
    }

    suspend fun obtenerProductos(
        categoriaId: Int? = null,
        busqueda: String? = null,
        destacados: Int? = null
    ): Result<List<dataProducto>> {
        return try {
            val response = RetrofitCliente.apiService.obtenerProductos(categoriaId, busqueda, destacados)
            val body = response.body()
            if (response.isSuccessful && body != null && body.status && body.data != null) {
                Result.success(body.data)
            } else {
                Result.failure(Exception(body?.message ?: "No se pudieron cargar los productos"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.localizedMessage ?: "Sin conexión"}"))
        }
    }

    suspend fun obtenerDetalleProducto(productoId: Int): Result<dataDetalleProducto> {
        return try {
            val response = RetrofitCliente.apiService.obtenerDetalleProducto(productoId)
            val body = response.body()
            if (response.isSuccessful && body != null && body.status && body.data != null) {
                Result.success(body.data)
            } else {
                Result.failure(Exception(body?.message ?: "No se pudo cargar el detalle del producto"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.localizedMessage ?: "Sin conexión"}"))
        }
    }
}