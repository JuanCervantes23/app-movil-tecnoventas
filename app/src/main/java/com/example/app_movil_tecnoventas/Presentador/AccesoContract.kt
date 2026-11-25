package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.dataCliente
import com.example.app_movil_tecnoventas.Modelo.dataPreguntaSeguridad

interface AccesoContract {

    interface LoginView {
        fun mostrarCargando(mostrar: Boolean)
        fun loginExitoso(cliente: dataCliente)
        fun mostrarError(mensaje: String)
    }

    interface LoginPresenter {
        fun iniciarSesion(correo: String, password: String)
        fun onDestroy()
    }

    interface RegistroView {
        fun mostrarCargando(mostrar: Boolean)
        fun cargarPreguntas(preguntas: List<dataPreguntaSeguridad>)
        fun registroExitoso(cliente: dataCliente)
        fun mostrarError(mensaje: String)
    }

    interface RegistroPresenter {
        fun obtenerPreguntasSeguridad()
        fun registrarUsuario(
            nombre: String,
            apellidos: String,
            correo: String,
            telefono: String,
            password: String,
            confirmarPassword: String,
            preguntaId: Int,
            respuestaSeguridad: String
        )
        fun onDestroy()
    }
}