package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.clsTiendaModelo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class clsComprarPresentador(
    private var view: TiendaContract.ComprarView?,
    private val modelo: clsTiendaModelo = clsTiendaModelo()
) : TiendaContract.ComprarPresenter {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun cargarCategorias() {
        scope.launch {
            val res = modelo.obtenerCategorias()
            res.fold(
                onSuccess = { lista -> view?.mostrarCategorias(lista) },
                onFailure = { error -> view?.mostrarError(error.message ?: "Error al cargar categorías") }
            )
        }
    }

    override fun cargarProductos(categoriaId: Int?) {
        view?.mostrarCargando(true)
        scope.launch {
            val idFiltro = if (categoriaId != null && categoriaId > 0) categoriaId else null
            val res = modelo.obtenerProductos(categoriaId = idFiltro)
            view?.mostrarCargando(false)

            res.fold(
                onSuccess = { lista -> view?.mostrarProductos(lista) },
                onFailure = { error -> view?.mostrarError(error.message ?: "Error al cargar catálogo") }
            )
        }
    }

    override fun onDestroy() {
        job.cancel()
        view = null
    }
}