package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Adaptador.ProductoHorizontalAdaptador
import com.example.app_movil_tecnoventas.Modelo.SessionManager
import com.example.app_movil_tecnoventas.Modelo.dataCategoria
import com.example.app_movil_tecnoventas.Modelo.dataProducto
import com.example.app_movil_tecnoventas.Presentador.TiendaContract
import com.example.app_movil_tecnoventas.Presentador.clsInicioPresentador
import com.example.app_movil_tecnoventas.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.jvm.java

class MainActivity : AppCompatActivity(), TiendaContract.InicioView {

    private lateinit var tvSaludoUsuario: TextView
    private lateinit var btnCerrarSesionMain: MaterialButton
    private lateinit var cardIrBuscar: MaterialCardView
    private lateinit var btnVerTodoCatalogo: MaterialButton
    private lateinit var tvVerMasDestacados: TextView
    private lateinit var chipGroupCategoriasInicio: ChipGroup
    private lateinit var progressInicio: ProgressBar
    private lateinit var rvProductosDestacados: RecyclerView
    private lateinit var btnInfoTienda: MaterialButton

    private lateinit var adaptadorDestacados: ProductoHorizontalAdaptador
    private lateinit var presenter: clsInicioPresentador
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        tvSaludoUsuario = findViewById(R.id.tvSaludoUsuario)
        btnCerrarSesionMain = findViewById(R.id.btnCerrarSesionMain)
        cardIrBuscar = findViewById(R.id.cardIrBuscar)
        btnVerTodoCatalogo = findViewById(R.id.btnVerTodoCatalogo)
        tvVerMasDestacados = findViewById(R.id.tvVerMasDestacados)
        chipGroupCategoriasInicio = findViewById(R.id.chipGroupCategoriasInicio)
        progressInicio = findViewById(R.id.progressInicio)
        rvProductosDestacados = findViewById(R.id.rvProductosDestacados)
        btnInfoTienda = findViewById(R.id.btnInfoTienda)

        val nombre = sessionManager.obtenerNombreCompleto().ifBlank { "Cliente" }
        tvSaludoUsuario.text = "Hola, $nombre"

        rvProductosDestacados.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adaptadorDestacados = ProductoHorizontalAdaptador(emptyList()) { producto ->
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("producto_id", producto.id)
            startActivity(intent)
        }
        rvProductosDestacados.adapter = adaptadorDestacados

        BottomNavHelper.configurarBarra(this, "INICIO")

        cardIrBuscar.setOnClickListener {
            startActivity(Intent(this, BuscarActivity::class.java))
        }

        btnVerTodoCatalogo.setOnClickListener {
            startActivity(Intent(this, ComprarActivity::class.java))
        }

        tvVerMasDestacados.setOnClickListener {
            startActivity(Intent(this, ComprarActivity::class.java))
        }

        btnInfoTienda.setOnClickListener {
            BottomNavHelper.abrirModuloEquipo(
                this,
                "com.example.app_movil_tecnoventas.Vista.InfoTiendaActivity",
                "Módulo Informativo (Tomasa) pendiente de integrar"
            )
        }

        btnCerrarSesionMain.setOnClickListener {
            sessionManager.cerrarSesion()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        presenter = clsInicioPresentador(this)
        presenter.cargarInicio()
    }

    override fun mostrarCargando(mostrar: Boolean) {
        progressInicio.visibility = if (mostrar) View.VISIBLE else View.GONE
    }

    override fun mostrarCategorias(categorias: List<dataCategoria>) {
        chipGroupCategoriasInicio.removeAllViews()
        for (cat in categorias) {
            val chip = Chip(this).apply {
                text = cat.nombre
                isClickable = true
                setOnClickListener {
                    val intent = Intent(this@MainActivity, ComprarActivity::class.java)
                    intent.putExtra("categoria_id", cat.id)
                    startActivity(intent)
                }
            }
            chipGroupCategoriasInicio.addView(chip)
        }
    }

    override fun mostrarDestacados(productos: List<dataProducto>) {
        adaptadorDestacados.actualizarLista(productos)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}