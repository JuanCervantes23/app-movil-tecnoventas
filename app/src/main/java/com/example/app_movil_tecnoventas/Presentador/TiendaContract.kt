package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.dataCategoria
import com.example.app_movil_tecnoventas.Modelo.dataProducto

interface TiendaContract {

    interface InicioView {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarCategorias(categorias: List<dataCategoria>)
        fun mostrarDestacados(productos: List<dataProducto>)
        fun mostrarError(mensaje: String)
    }

    interface InicioPresenter {
        fun cargarInicio()
        fun onDestroy()
    }

    interface ComprarView {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarCategorias(categorias: List<dataCategoria>)
        fun mostrarProductos(productos: List<dataProducto>)
        fun mostrarError(mensaje: String)
    }

    interface ComprarPresenter {
        fun cargarCategorias()
        fun cargarProductos(categoriaId: Int? = null)
        fun onDestroy()
    }
}