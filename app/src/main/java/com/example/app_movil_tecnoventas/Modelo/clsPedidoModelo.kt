package com.example.app_movil_tecnoventas.Modelo

import java.util.Collections.emptyList
import kotlin.collections.map

class clsPedidoModelo {
    suspend fun listarMetodosPago(): List<dataMetodoPago> {
        val respuesta = RetrofitCliente.apiService.listarMetodosPago()
        return if (respuesta.status && respuesta.data != null) respuesta.data else emptyList()
    }

    suspend fun crearPedido(
        idCliente: Int,
        idMetodoPago: Int,
        direccionEnvio: String,
        itemsBolsa: List<dataItemBolsa>
    ): ApiResponse<PedidoResponse> {
        val itemsReq = itemsBolsa.map {
            PedidoItemRequest(noSerie = it.noSerie, cantidad = it.cantidad)
        }
        val req = PedidoRequest(
            idCliente = idCliente,
            idMetodoPago = idMetodoPago,
            direccionEnvio = direccionEnvio,
            items = itemsReq
        )
        return RetrofitCliente.apiService.crearPedido(req)
    }

    suspend fun obtenerPedidosCliente(idCliente: Int): dataPedidosClienteResponse? {
        val respuesta = RetrofitCliente.apiService.obtenerPedidosCliente(idCliente)
        return if (respuesta.status) respuesta.data else null
    }

}