package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Adaptador.BolsaAdaptador
import com.example.app_movil_tecnoventas.Modelo.dataItemBolsa
import com.example.app_movil_tecnoventas.Presentador.clsBolsaPresentador
import com.example.app_movil_tecnoventas.R
import java.text.NumberFormat
import java.util.Locale

class BolsaActivity : AppCompatActivity() {
    private lateinit var presenter: clsBolsaPresentador
    private lateinit var txtSubtituloArticulos: TextView
    private lateinit var rvBolsa: RecyclerView
    private lateinit var txtSubtotalLabel: TextView
    private lateinit var txtSubtotalMonto: TextView
    private lateinit var txtTotalMonto: TextView
    private lateinit var btnFinalizarCompra: Button
    private lateinit var adaptador: BolsaAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bolsa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        txtSubtituloArticulos = findViewById(R.id.txtSubtituloBolsa)
        rvBolsa = findViewById(R.id.rvItemsBolsa)
        txtSubtotalLabel = findViewById(R.id.txtSubtotalLabelBolsa)
        txtSubtotalMonto = findViewById(R.id.txtSubtotalMontoBolsa)
        txtTotalMonto = findViewById(R.id.txtTotalMontoBolsa)
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompraBolsa)

        presenter = clsBolsaPresentador(this)
        adaptador = BolsaAdaptador(
            lista = emptyList(),
            alCambiarCantidad = { serie, cant -> presenter.cambiarCantidad(serie, cant) },
            alEliminar = { serie -> presenter.eliminarItem(serie) }
        )
        rvBolsa.layoutManager = LinearLayoutManager(this)
        rvBolsa.adapter = adaptador

        btnFinalizarCompra.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.cargarBolsa()
        BottomNavHelper.configurar(this, BottomNavHelper.Tab.BOLSA)
    }

    fun mostrarBolsa(items: List<dataItemBolsa>, totalArticulos: Int, totalPagar: Double) {
        val formato = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        txtSubtituloArticulos.text = "$totalArticulos artículos agregados"
        txtSubtotalLabel.text = "Subtotal ($totalArticulos artículos)"
        txtSubtotalMonto.text = "${formato.format(totalPagar)} MXN"
        txtTotalMonto.text = "${formato.format(totalPagar)} MXN"
        adaptador.actualizarLista(items)

        btnFinalizarCompra.visibility = if (items.isEmpty()) View.GONE else View.VISIBLE
        BottomNavHelper.actualizarBadgeBolsa(this)
        if (items.isEmpty()) {
            Toast.makeText(this, "Tu bolsa está vacía", Toast.LENGTH_SHORT).show()
        }
    }
}