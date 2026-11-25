package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Adaptador.ProductoGridAdaptador
import com.example.app_movil_tecnoventas.Modelo.dataCategoria
import com.example.app_movil_tecnoventas.Modelo.dataProducto
import com.example.app_movil_tecnoventas.Presentador.TiendaContract
import com.example.app_movil_tecnoventas.Presentador.clsComprarPresentador
import com.example.app_movil_tecnoventas.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class ComprarActivity : AppCompatActivity(), TiendaContract.ComprarView {

    private lateinit var btnBuscarDesdeCatalogo: ImageButton
    private lateinit var chipGroupCategoriasCatalogo: ChipGroup
    private lateinit var tvContadorCatalogo: TextView
    private lateinit var progressCatalogo: ProgressBar
    private lateinit var rvProductosCatalogo: RecyclerView

    private lateinit var adaptadorGrid: ProductoGridAdaptador
    private lateinit var presenter: clsComprarPresentador
    private var categoriaInicialId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar)

        categoriaInicialId = intent.getIntExtra("categoria_id", 0)

        btnBuscarDesdeCatalogo = findViewById(R.id.btnBuscarDesdeCatalogo)
        chipGroupCategoriasCatalogo = findViewById(R.id.chipGroupCategoriasCatalogo)
        tvContadorCatalogo = findViewById(R.id.tvContadorCatalogo)
        progressCatalogo = findViewById(R.id.progressCatalogo)
        rvProductosCatalogo = findViewById(R.id.rvProductosCatalogo)

        rvProductosCatalogo.layoutManager = GridLayoutManager(this, 2)
        adaptadorGrid = ProductoGridAdaptador(emptyList()) { producto ->
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("producto_id", producto.id)
            startActivity(intent)
        }
        rvProductosCatalogo.adapter = adaptadorGrid

        BottomNavHelper.configurarBarra(this, "CATALOGO")

        btnBuscarDesdeCatalogo.setOnClickListener {
            startActivity(Intent(this, BuscarActivity::class.java))
        }

        presenter = clsComprarPresentador(this)
        presenter.cargarCategorias()
        presenter.cargarProductos(if (categoriaInicialId > 0) categoriaInicialId else null)
    }

    override fun mostrarCargando(mostrar: Boolean) {
        progressCatalogo.visibility = if (mostrar) View.VISIBLE else View.GONE
    }

    override fun mostrarCategorias(categorias: List<dataCategoria>) {
        chipGroupCategoriasCatalogo.removeAllViews()

        val chipTodas = Chip(this).apply {
            id = View.generateViewId()
            text = "Todas"
            isCheckable = true
            isChecked = (categoriaInicialId == 0)
            setOnClickListener { presenter.cargarProductos(null) }
        }
        chipGroupCategoriasCatalogo.addView(chipTodas)

        for (cat in categorias) {
            val chip = Chip(this).apply {
                id = View.generateViewId()
                text = cat.nombre
                isCheckable = true
                isChecked = (cat.id == categoriaInicialId)
                setOnClickListener { presenter.cargarProductos(cat.id) }
            }
            chipGroupCategoriasCatalogo.addView(chip)
        }
    }

    override fun mostrarProductos(productos: List<dataProducto>) {
        adaptadorGrid.actualizarLista(productos)
        tvContadorCatalogo.text = "Mostrando ${productos.size} productos"
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}