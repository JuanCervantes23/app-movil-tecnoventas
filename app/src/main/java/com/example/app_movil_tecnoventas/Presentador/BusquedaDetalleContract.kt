package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.dataDetalleProducto
import com.example.app_movil_tecnoventas.Modelo.dataProducto

interface BusquedaDetalleContract {

    interface BuscarView {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarResultados(productos: List<dataProducto>)
        fun mostrarSinResultados(termino: String)
        fun mostrarError(mensaje: String)
    }

    interface BuscarPresenter {
        fun buscarProductos(termino: String)
        fun onDestroy()
    }

    interface DetalleView {
        fun mostrarCargando(mostrar: Boolean)
        fun mostrarDetalle(producto: dataDetalleProducto)
        fun mostrarError(mensaje: String)
    }

    interface DetallePresenter {
        fun cargarDetalle(productoId: Int)
        fun onDestroy()
    }
}