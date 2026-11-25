package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.clsTiendaModelo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class clsInicioPresentador(
    private var view: TiendaContract.InicioView?,
    private val modelo: clsTiendaModelo = clsTiendaModelo()
) : TiendaContract.InicioPresenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun cargarInicio() {
        view?.mostrarCargando(true)
        scope.launch {
            val resCategorias = modelo.obtenerCategorias()
            resCategorias.fold(
                onSuccess = { lista -> view?.mostrarCategorias(lista) },
                onFailure = { error -> view?.mostrarError(error.message ?: "Error al cargar categorías") }
            )

            val resDestacados = modelo.obtenerProductos(destacados = 1)
            view?.mostrarCargando(false)

            resDestacados.fold(
                onSuccess = { lista -> view?.mostrarDestacados(lista) },
                onFailure = { error -> view?.mostrarError(error.message ?: "Error al cargar destacados") }
            )
        }
    }

    override fun onDestroy() {
        job.cancel()
        view = null
    }
}