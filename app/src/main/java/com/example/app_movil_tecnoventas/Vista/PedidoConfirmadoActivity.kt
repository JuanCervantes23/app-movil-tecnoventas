package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_movil_tecnoventas.Modelo.PedidoResponse
import com.example.app_movil_tecnoventas.R
import kotlinx.serialization.json.Json
import java.text.NumberFormat
import java.util.Locale

class PedidoConfirmadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedido_confirmado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        val txtOrden = findViewById<TextView>(R.id.txtFolioConfirmado)
        val txtDetalleItems = findViewById<TextView>(R.id.txtItemsConfirmado)
        val txtDireccion = findViewById<TextView>(R.id.txtDireccionConfirmado)
        val txtMetodo = findViewById<TextView>(R.id.txtMetodoConfirmado)
        val txtTotalPagado = findViewById<TextView>(R.id.txtTotalConfirmado)
        val btnRastrear = findViewById<Button>(R.id.btnRastrearPedido)
        val btnVolverTienda = findViewById<Button>(R.id.btnVolverTienda)

        val rawJson = intent.getStringExtra("pedido_json")
        if (!rawJson.isNullOrEmpty()) {
            val pedido = Json.decodeFromString<PedidoResponse>(rawJson)
            val formato = NumberFormat.getCurrencyInstance(Locale("es", "MX"))

            txtOrden.text = "Orden #${pedido.folio}"
            txtDetalleItems.text = pedido.items.joinToString("
                ") {
                "${it.nombre} (x${it.cantidad}) — ${formato.format(it.subtotal)}"
        }
        txtDireccion.text = "📍 ${pedido.direccionEnvio}"
        txtMetodo.text = "💳 ${pedido.metodoPago} • Estado: ${pedido.estado}"
        txtTotalPagado.text = "${formato.format(pedido.total)} MXN"
    }

    btnRastrear.setOnClickListener {
        startActivity(Intent(this, MisPedidosActivity::class.java))
        finish()
    }
    btnVolverTienda.setOnClickListener {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
}
