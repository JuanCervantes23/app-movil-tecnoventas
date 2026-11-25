package com.example.app_movil_tecnoventas.Vista

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.app_movil_tecnoventas.Modelo.dataDetalleProducto
import com.example.app_movil_tecnoventas.Presentador.BusquedaDetalleContract
import com.example.app_movil_tecnoventas.Presentador.clsDetalleProductoPresentador
import com.example.app_movil_tecnoventas.R
import com.google.android.material.button.MaterialButton
import java.util.Locale

class DetalleProductoActivity : AppCompatActivity(), BusquedaDetalleContract.DetalleView {

    private lateinit var btnVolverDetalle: ImageButton
    private lateinit var btnIrBolsaDetalle: MaterialButton
    private lateinit var progressDetalle: ProgressBar
    private lateinit var scrollDetalleContenido: ScrollView
    private lateinit var ivDetalleProducto: ImageView
    private lateinit var tvDetalleCategoriaMarca: TextView
    private lateinit var tvDetalleNombre: TextView
    private lateinit var tvDetallePrecio: TextView
    private lateinit var tvDetallePrecioAnterior: TextView
    private lateinit var tvDetalleStock: TextView
    private lateinit var btnMenosCantidad: MaterialButton
    private lateinit var tvCantidadSeleccionada: TextView
    private lateinit var btnMasCantidad: MaterialButton
    private lateinit var tvDetalleDescripcion: TextView
    private lateinit var tvDetalleEspecificaciones: TextView
    private lateinit var btnAgregarBolsa: MaterialButton

    private lateinit var presenter: clsDetalleProductoPresentador
    private var productoActual: dataDetalleProducto? = null
    private var cantidad: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        btnVolverDetalle = findViewById(R.id.btnVolverDetalle)
        btnIrBolsaDetalle = findViewById(R.id.btnIrBolsaDetalle)
        progressDetalle = findViewById(R.id.progressDetalle)
        scrollDetalleContenido = findViewById(R.id.scrollDetalleContenido)
        ivDetalleProducto = findViewById(R.id.ivDetalleProducto)
        tvDetalleCategoriaMarca = findViewById(R.id.tvDetalleCategoriaMarca)
        tvDetalleNombre = findViewById(R.id.tvDetalleNombre)
        tvDetallePrecio = findViewById(R.id.tvDetallePrecio)
        tvDetallePrecioAnterior = findViewById(R.id.tvDetallePrecioAnterior)
        tvDetalleStock = findViewById(R.id.tvDetalleStock)
        btnMenosCantidad = findViewById(R.id.btnMenosCantidad)
        tvCantidadSeleccionada = findViewById(R.id.tvCantidadSeleccionada)
        btnMasCantidad = findViewById(R.id.btnMasCantidad)
        tvDetalleDescripcion = findViewById(R.id.tvDetalleDescripcion)
        tvDetalleEspecificaciones = findViewById(R.id.tvDetalleEspecificaciones)
        btnAgregarBolsa = findViewById(R.id.btnAgregarBolsa)

        btnVolverDetalle.setOnClickListener { finish() }

        btnIrBolsaDetalle.setOnClickListener {
            BottomNavHelper.abrirModuloEquipo(
                this,
                "com.example.app_movil_tecnoventas.Vista.BolsaActivity",
                "Módulo de Bolsa (Ángel David) pendiente de integrar"
            )
        }

        btnMenosCantidad.setOnClickListener {
            if (cantidad > 1) {
                cantidad--
                tvCantidadSeleccionada.text = cantidad.toString()
            }
        }

        btnMasCantidad.setOnClickListener {
            val stockMax = productoActual?.stock ?: 1
            if (cantidad < stockMax) {
                cantidad++
                tvCantidadSeleccionada.text = cantidad.toString()
            } else {
                Toast.makeText(this, "Stock máximo disponible: $stockMax", Toast.LENGTH_SHORT).show()
            }
        }

        btnAgregarBolsa.setOnClickListener {
            val prod = productoActual ?: return@setOnClickListener
            if (prod.stock <= 0) {
                Toast.makeText(this, "Producto agotado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            guardarEnBolsaLocal(prod, cantidad)
            Toast.makeText(
                this,
                "Agregado a la bolsa: ${prod.nombre} (x$cantidad)",
                Toast.LENGTH_SHORT
            ).show()
        }

        val productoId = intent.getIntExtra("producto_id", 0)
        presenter = clsDetalleProductoPresentador(this)
        presenter.cargarDetalle(productoId)
    }

    private fun guardarEnBolsaLocal(producto: dataDetalleProducto, cant: Int) {
        val prefs = getSharedPreferences("tecnoventas_bolsa", Context.MODE_PRIVATE)
        val key = "item_${producto.id}"
        val existente = prefs.getString(key, null)
        val cantidadTotal = if (existente != null) {
            val partes = existente.split("|")
            val prevCant = partes.getOrNull(3)?.toIntOrNull() ?: 0
            (prevCant + cant).coerceAtMost(producto.stock)
        } else {
            cant
        }
        val registro = "${producto.id}|${producto.nombre}|${producto.precio}|$cantidadTotal|${producto.imagen}"
        prefs.edit().putString(key, registro).apply()
    }

    override fun mostrarCargando(mostrar: Boolean) {
        progressDetalle.visibility = if (mostrar) View.VISIBLE else View.GONE
        scrollDetalleContenido.visibility = if (mostrar) View.GONE else View.VISIBLE
    }

    override fun mostrarDetalle(producto: dataDetalleProducto) {
        productoActual = producto
        cantidad = 1
        tvCantidadSeleccionada.text = "1"

        tvDetalleCategoriaMarca.text = if (producto.marca.isNotBlank()) {
            "${producto.categoria} • ${producto.marca}"
        } else {
            producto.categoria
        }
        tvDetalleNombre.text = producto.nombre
        tvDetallePrecio.text = String.format(Locale.US, "$%,.2f", producto.precio)

        if (producto.precioAnterior != null && producto.precioAnterior > producto.precio) {
            tvDetallePrecioAnterior.visibility = View.VISIBLE
            tvDetallePrecioAnterior.text = String.format(Locale.US, "$%,.2f", producto.precioAnterior)
            tvDetallePrecioAnterior.paintFlags =
                tvDetallePrecioAnterior.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvDetallePrecioAnterior.visibility = View.GONE
        }

        if (producto.stock > 0) {
            tvDetalleStock.text = "Disponibles: ${producto.stock} unidades"
            tvDetalleStock.setTextColor(Color.parseColor("#2E7D32"))
            btnAgregarBolsa.isEnabled = true
            btnAgregarBolsa.text = "Agregar a la Bolsa"
        } else {
            tvDetalleStock.text = "Agotado"
            tvDetalleStock.setTextColor(Color.parseColor("#C62828"))
            btnAgregarBolsa.isEnabled = false
            btnAgregarBolsa.text = "Sin existencias"
        }

        tvDetalleDescripcion.text = producto.descripcion.ifBlank { "Sin descripción disponible." }
        tvDetalleEspecificaciones.text =
            producto.especificaciones.ifBlank { "Sin especificaciones técnicas registradas." }

        val urlImagen = if (producto.imagen.startsWith("http")) {
            producto.imagen
        } else {
            "https://equipo4.grupoc.io/uploads/img/${producto.imagen}"
        }

        Glide.with(this)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(ivDetalleProducto)
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}