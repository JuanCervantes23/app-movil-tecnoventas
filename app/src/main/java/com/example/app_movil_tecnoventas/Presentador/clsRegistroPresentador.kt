package com.example.app_movil_tecnoventas.Presentador

import android.util.Patterns
import com.example.app_movil_tecnoventas.Modelo.ErrorParser
import com.example.app_movil_tecnoventas.Modelo.SessionManager
import com.example.app_movil_tecnoventas.Modelo.clsAccesoModelo
import com.example.app_movil_tecnoventas.Modelo.dataPreguntaSeguridad
import com.example.app_movil_tecnoventas.Vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsRegistroPresentador(private val vista: RegistroActivity) {
    private val modelo = clsAccesoModelo()
    private val sessionManager = SessionManager(vista)
    private var listaPreguntas: List<dataPreguntaSeguridad> = emptyList()

    fun cargarPreguntasSeguridad() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listaPreguntas = modelo.obtenerPreguntasSeguridad()
                vista.mostrarPreguntas(listaPreguntas)
            } catch (e: Exception) {
                vista.mostrarError("No se pudieron cargar las preguntas: ${ErrorParser.obtenerMensaje(e)}")
            }
        }
    }

    fun registrar(
        nombre: String,
        apPaterno: String,
        apMaterno: String,
        correo: String,
        telefono: String,
        password: String,
        posicionPregunta: Int,
        respuestaSeguridad: String,
        aceptoTerminos: Boolean
    ) {
        if (nombre.isEmpty() || apPaterno.isEmpty() || correo.isEmpty() || password.isEmpty() ||
            respuestaSeguridad.isEmpty()
        ) {
            vista.mostrarError("Completa todos los campos obligatorios")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            vista.mostrarError("El formato del correo electrónico no es válido")
            return
        }
        if (telefono.isNotEmpty() && !Regex("^[0-9]{10}$").matches(telefono)) {
            vista.mostrarError("El teléfono debe tener 10 dígitos numéricos")
            return
        }
        if (password.length < 6) {
            vista.mostrarError("La contraseña debe tener al menos 6 caracteres")
            return
        }
        if (listaPreguntas.isEmpty() || posicionPregunta !in listaPreguntas.indices) {
            vista.mostrarError("Selecciona una pregunta de seguridad válida")
            return
        }
        if (!aceptoTerminos) {
            vista.mostrarError("Debes aceptar los Términos y Condiciones")
            return
        }

        val idPregunta = listaPreguntas[posicionPregunta].idPregunta
        vista.mostrarCargando(true)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = modelo.registrarCliente(
                    nombre = nombre,
                    apPaterno = apPaterno,
                    apMaterno = apMaterno,
                    correo = correo,
                    telefono = telefono,
                    password = password,
                    idPregunta = idPregunta,
                    respuestaSeguridad = respuestaSeguridad
                )
                if (respuesta.status && respuesta.data != null) {
                    sessionManager.guardarCliente(respuesta.data)
                    vista.registroExitoso(respuesta.message)
                } else {
                    vista.mostrarError(respuesta.message.ifEmpty { "No se pudo completar el registro" })
                }
            } catch (e: Exception) {
                vista.mostrarError(ErrorParser.obtenerMensaje(e))
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }
}