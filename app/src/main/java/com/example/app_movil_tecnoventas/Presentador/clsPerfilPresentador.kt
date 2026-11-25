package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.ErrorParser
import com.example.app_movil_tecnoventas.Modelo.SessionManager
import com.example.app_movil_tecnoventas.Modelo.clsPedidoModelo
import com.example.app_movil_tecnoventas.Modelo.clsPerfilModelo
import com.example.app_movil_tecnoventas.Vista.PerfilActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsPerfilPresentador(private val vista: PerfilActivity) {
    private val perfilModelo = clsPerfilModelo()
    private val pedidoModelo = clsPedidoModelo()
    private val sessionManager = SessionManager(vista)

    fun cargarPerfil() {
        val local = sessionManager.obtenerCliente() ?: return
        vista.mostrarDatosCliente(local)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val remoto = perfilModelo.obtenerPerfil(local.id)
                if (remoto != null) {
                    sessionManager.guardarCliente(remoto)
                    vista.mostrarDatosCliente(remoto)
                }
                val pedidosRes = pedidoModelo.obtenerPedidosCliente(local.id)
                vista.mostrarPedidoActivo(pedidosRes?.pedidoActivo)
            } catch (e: Exception) {
                vista.mostrarError(ErrorParser.obtenerMensaje(e))
            }
        }
    }

    fun cerrarSesion() {
        sessionManager.cerrarSesion()
        vista.navegarALogin()
    }
}
