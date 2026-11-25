package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.ErrorParser
import com.example.app_movil_tecnoventas.Modelo.SessionManager
import com.example.app_movil_tecnoventas.Modelo.clsPedidoModelo
import com.example.app_movil_tecnoventas.Vista.MisPedidosActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsMisPedidosPresentador(private val vista: MisPedidosActivity) {
    private val modelo = clsPedidoModelo()
    private val sessionManager = SessionManager(vista)

    fun cargarPedidos() {
        val cliente = sessionManager.obtenerCliente() ?: return
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val res = modelo.obtenerPedidosCliente(cliente.id)
                vista.mostrarHistorial(res?.historial ?: emptyList())
            } catch (e: Exception) {
                vista.mostrarError(ErrorParser.obtenerMensaje(e))
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }
}
