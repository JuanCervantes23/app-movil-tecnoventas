package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.clsTiendaModelo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class clsDetalleProductoPresentador(
    private var view: BusquedaDetalleContract.DetalleView?,
    private val modelo: clsTiendaModelo = clsTiendaModelo()
) : BusquedaDetalleContract.DetallePresenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun cargarDetalle(productoId: Int) {
        if (productoId <= 0) {
            view?.mostrarError("Producto no válido")
            return
        }

        view?.mostrarCargando(true)
        scope.launch {
            val resultado = modelo.obtenerDetalleProducto(productoId)
            view?.mostrarCargando(false)

            resultado.fold(
                onSuccess = { detalle -> view?.mostrarDetalle(detalle) },
                onFailure = { error -> view?.mostrarError(error.message ?: "No se pudo cargar el producto") }
            )
        }
    }

    override fun onDestroy() {
        job.cancel()
        view = null
    }
}