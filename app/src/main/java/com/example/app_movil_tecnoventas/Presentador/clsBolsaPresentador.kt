package com.example.app_movil_tecnoventas.Presentador

import com.example.app_movil_tecnoventas.Modelo.BolsaManager
import com.example.app_movil_tecnoventas.Vista.BolsaActivity

class clsBolsaPresentador(private val vista: BolsaActivity) {
    private val bolsaManager = BolsaManager(vista)

    fun cargarBolsa() {
        val items = bolsaManager.obtenerItems()
        val totalArticulos = bolsaManager.totalArticulos()
        val totalPagar = bolsaManager.totalPagar()
        vista.mostrarBolsa(items, totalArticulos, totalPagar)
    }

    fun cambiarCantidad(noSerie: String, nuevaCantidad: Int) {
        bolsaManager.actualizarCantidad(noSerie, nuevaCantidad)
        cargarBolsa()
    }

    fun eliminarItem(noSerie: String) {
        bolsaManager.eliminarProducto(noSerie)
        cargarBolsa()
    }
}