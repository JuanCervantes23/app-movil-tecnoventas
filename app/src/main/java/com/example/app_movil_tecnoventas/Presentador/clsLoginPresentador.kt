package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.ErrorParser
import com.example.app_movil_tecnoventas.Modelo.SessionManager
import com.example.app_movil_tecnoventas.Modelo.clsAccesoModelo
import com.example.app_movil_tecnoventas.Vista.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsLoginPresentador(private val vista: LoginActivity) {
    private val modelo = clsAccesoModelo()
    private val sessionManager = SessionManager(vista)

    fun verificarSesionPrevia() {
        if (sessionManager.haySesionActiva()) {
            vista.navegarAInicio()
        }
    }

    fun iniciarSesion(correo: String, password: String) {
        if (correo.isEmpty() || password.isEmpty()) {
            vista.mostrarError("Ingresa tu correo electrónico y contraseña")
            return
        }

        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.iniciarSesion(correo, password)
                if (respuesta.status && respuesta.data != null) {
                    sessionManager.guardarCliente(respuesta.data)
                    vista.loginExitoso(respuesta.message)
                } else {
                    vista.mostrarError(respuesta.message.ifEmpty { "Credenciales incorrectas" })
                }
            } catch (e: Exception) {
                vista.mostrarError(ErrorParser.obtenerMensaje(e))
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }
}