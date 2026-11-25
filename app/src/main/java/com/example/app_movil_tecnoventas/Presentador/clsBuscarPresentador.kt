package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.clsTiendaModelo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class clsBuscarPresentador(
    private var view: BusquedaDetalleContract.BuscarView?,
    private val modelo: clsTiendaModelo = clsTiendaModelo()
) : BusquedaDetalleContract.BuscarPresenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var busquedaJob: Job? = null

    override fun buscarProductos(termino: String) {
        val query = termino.trim()
        if (query.isEmpty()) {
            view?.mostrarResultados(emptyList())
            return
        }

        busquedaJob?.cancel()
        view?.mostrarCargando(true)

        busquedaJob = scope.launch {
            val resultado = modelo.obtenerProductos(busqueda = query)
            view?.mostrarCargando(false)

            resultado.fold(
                onSuccess = { lista ->
                    if (lista.isEmpty()) {
                        view?.mostrarSinResultados(query)
                    } else {
                        view?.mostrarResultados(lista)
                    }
                },
                onFailure = { error ->
                    view?.mostrarError(error.message ?: "Error al buscar productos")
                }
            )
        }
    }

    override fun onDestroy() {
        busquedaJob?.cancel()
        job.cancel()
        view = null
    }
}