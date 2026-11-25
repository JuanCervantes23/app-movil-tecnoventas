package com.example.app_movil_tecnoventas.Vista

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Adaptador.PedidoAdaptador
import com.example.app_movil_tecnoventas.Modelo.dataPedido
import com.example.app_movil_tecnoventas.Presentador.clsMisPedidosPresentador
import com.example.app_movil_tecnoventas.R

class MisPedidosActivity : AppCompatActivity() {
    private lateinit var presenter: clsMisPedidosPresentador
    private lateinit var rvPedidos: RecyclerView
    private lateinit var pbPedidos: ProgressBar
    private lateinit var adaptador: PedidoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_pedidos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        findViewById<ImageButton>(R.id.btnAtrasPedidos).setOnClickListener { finish() }
        rvPedidos = findViewById(R.id.rvMisPedidos)
        pbPedidos = findViewById(R.id.pbMisPedidos)

        adaptador = PedidoAdaptador(emptyList())
        rvPedidos.layoutManager = LinearLayoutManager(this)
        rvPedidos.adapter = adaptador

        presenter = clsMisPedidosPresentador(this)
        presenter.cargarPedidos()
    }

    fun mostrarCargando(cargando: Boolean) {
        pbPedidos.visibility = if (cargando) View.VISIBLE else View.GONE
    }

    fun mostrarHistorial(lista: List<dataPedido>) {
        adaptador.actualizarLista(lista)
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
