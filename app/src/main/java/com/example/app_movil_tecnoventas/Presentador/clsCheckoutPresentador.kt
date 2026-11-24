package com.example.app_movil_tecnoventas.Presentador

    import com.example.app_movil_tecnoventas.Modelo.BolsaManager
    import com.example.app_movil_tecnoventas.Modelo.ErrorParser
    import com.example.app_movil_tecnoventas.Modelo.SessionManager
    import com.example.app_movil_tecnoventas.Modelo.clsPedidoModelo
    import com.example.app_movil_tecnoventas.Modelo.clsPerfilModelo
    import com.example.app_movil_tecnoventas.Vista.CheckoutActivity
    import kotlinx.coroutines.CoroutineScope
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch

    class clsCheckoutPresentador(private val vista: CheckoutActivity) {
        private val pedidoModelo = clsPedidoModelo()
        private val perfilModelo = clsPerfilModelo()
        private val bolsaManager = BolsaManager(vista)
        private val sessionManager = SessionManager(vista)

        fun cargarDatosCheckout() {
            val items = bolsaManager.obtenerItems()
            vista.mostrarResumenArticulos(items, bolsaManager.totalArticulos(), bolsaManager.totalPagar())

            val clienteLocal = sessionManager.obtenerCliente() ?: return
            vista.mostrarDireccionCliente(clienteLocal)

            vista.mostrarCargando(true)
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val metodos = pedidoModelo.listarMetodosPago()
                    vista.mostrarMetodosPago(metodos)

                    val perfilActualizado = perfilModelo.obtenerPerfil(clienteLocal.id)
                    if (perfilActualizado != null) {
                        sessionManager.guardarCliente(perfilActualizado)
                        vista.mostrarDireccionCliente(perfilActualizado)
                    }
                } catch (e: Exception) {
                    vista.mostrarError(ErrorParser.obtenerMensaje(e))
                } finally {
                    vista.mostrarCargando(false)
                }
            }
        }

        fun confirmarYPagar(idMetodoPago: Int) {
            val cliente = sessionManager.obtenerCliente()
            if (cliente == null) {
                vista.mostrarError("Debes iniciar sesión para finalizar la compra")
                return
            }
            val items = bolsaManager.obtenerItems()
            if (items.isEmpty()) {
                vista.mostrarError("Tu bolsa está vacía")
                return
            }
            if (cliente.direccionCompleta.isBlank()) {
                vista.mostrarError("Registra primero tu dirección de envío en Cambiar")
                return
            }

            vista.mostrarCargando(true)
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val respuesta = pedidoModelo.crearPedido(
                        idCliente = cliente.id,
                        idMetodoPago = idMetodoPago,
                        direccionEnvio = cliente.direccionCompleta,
                        itemsBolsa = items
                    )
                    if (respuesta.status && respuesta.data != null) {
                        bolsaManager.limpiarBolsa()
                        vista.pedidoConfirmado(respuesta.data)
                    } else {
                        vista.mostrarError(respuesta.message.ifEmpty { "No se pudo procesar el pedido" })
                    }
                } catch (e: Exception) {
                    vista.mostrarError(ErrorParser.obtenerMensaje(e))
                } finally {
                    vista.mostrarCargando(false)
                }
            }
        }
}