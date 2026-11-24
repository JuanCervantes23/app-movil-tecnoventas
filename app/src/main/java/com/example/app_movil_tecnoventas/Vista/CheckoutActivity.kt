package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Adaptador.MetodoPagoAdaptador
import com.example.app_movil_tecnoventas.Modelo.PedidoResponse
import com.example.app_movil_tecnoventas.Modelo.dataCliente
import com.example.app_movil_tecnoventas.Modelo.dataItemBolsa
import com.example.app_movil_tecnoventas.Modelo.dataMetodoPago
import com.example.app_movil_tecnoventas.Presentador.clsCheckoutPresentador
import com.example.app_movil_tecnoventas.R
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {
    private lateinit var presenter: clsCheckoutPresentador
    private lateinit var btnAtras: ImageButton
    private lateinit var btnCambiarDir: TextView
    private lateinit var txtNombreCliente: TextView
    private lateinit var txtDireccionCliente: TextView
    private lateinit var txtTelefonoCliente: TextView
    private lateinit var rvMetodosPago: RecyclerView
    private lateinit var txtResumenArticulos: TextView
    private lateinit var txtListaItemsResumen: TextView
    private lateinit var txtTotalCheckout: TextView
    private lateinit var btnConfirmarPagar: Button
    private lateinit var pbCheckout: ProgressBar
    private lateinit var adaptadorPago: MetodoPagoAdaptador

    private var idMetodoSeleccionado = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        btnAtras = findViewById(R.id.btnAtrasCheckout)
        btnCambiarDir = findViewById(R.id.txtCambiarDireccionCheckout)
        txtNombreCliente = findViewById(R.id.txtNombreClienteCheckout)
        txtDireccionCliente = findViewById(R.id.txtDireccionCheckout)
        txtTelefonoCliente = findViewById(R.id.txtTelefonoCheckout)
        rvMetodosPago = findViewById(R.id.rvMetodosPagoCheckout)
        txtResumenArticulos = findViewById(R.id.txtCantArticulosCheckout)
        txtListaItemsResumen = findViewById(R.id.txtListaItemsCheckout)
        txtTotalCheckout = findViewById(R.id.txtTotalCheckout)
        btnConfirmarPagar = findViewById(R.id.btnConfirmarPagar)
        pbCheckout = findViewById(R.id.pbCheckout)

        adaptadorPago = MetodoPagoAdaptador(emptyList()) { metodo ->
            idMetodoSeleccionado = metodo.idMetodoPago
        }
        rvMetodosPago.layoutManager = LinearLayoutManager(this)
        rvMetodosPago.adapter = adaptadorPago

        presenter = clsCheckoutPresentador(this)

        btnAtras.setOnClickListener { finish() }
        btnCambiarDir.setOnClickListener {
            startActivity(Intent(this, EditarPerfilActivity::class.java))
        }
        btnConfirmarPagar.setOnClickListener {
            presenter.confirmarYPagar(idMetodoSeleccionado)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.cargarDatosCheckout()
    }

    fun mostrarDireccionCliente(cliente: dataCliente) {
        txtNombreCliente.text = cliente.nombreCompleto()
        txtDireccionCliente.text = cliente.direccionCompleta.ifEmpty {
            "Sin dirección registrada (Toca 'Cambiar' para agregar)"
        }
        txtTelefonoCliente.text = if (cliente.telefono.isNotEmpty()) "Tel: ${cliente.telefono}" else ""
    }

    fun mostrarMetodosPago(lista: List<dataMetodoPago>) {
        adaptadorPago.actualizarLista(lista)
    }

    fun mostrarResumenArticulos(items: List<dataItemBolsa>, totalArticulos: Int, totalPagar: Double) {
        val formato = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        txtResumenArticulos.text = "$totalArticulos artículos"
        txtListaItemsResumen.text = items.joinToString("
            ") {
            "${it.nombre} (×${it.cantidad}) — ${formato.format(it.subtotal())}"
    }
    txtTotalCheckout.text = "${formato.format(totalPagar)} MXN"
}

fun mostrarCargando(cargando: Boolean) {
    pbCheckout.visibility = if (cargando) View.VISIBLE else View.GONE
    btnConfirmarPagar.isEnabled = !cargando
}

fun pedidoConfirmado(pedido: PedidoResponse) {
    val intent = Intent(this, PedidoConfirmadoActivity::class.java)
    intent.putExtra("pedido_json", Json.encodeToString(pedido))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    finish()
}

fun mostrarError(mensaje: String) {
    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
}
}