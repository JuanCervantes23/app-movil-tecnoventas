package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_movil_tecnoventas.Modelo.dataCliente
import com.example.app_movil_tecnoventas.Modelo.dataPedido
import com.example.app_movil_tecnoventas.Presentador.clsPerfilPresentador
import com.example.app_movil_tecnoventas.R

class PerfilActivity : AppCompatActivity() {
    private lateinit var presenter: clsPerfilPresentador
    private lateinit var txtIniciales: TextView
    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var cardPedidoActivo: LinearLayout
    private lateinit var txtTituloPedidoActivo: TextView
    private lateinit var txtSubtituloPedidoActivo: TextView
    private lateinit var txtBadgePedidosActivos: TextView
    private lateinit var txtFavoritosMenu: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        txtIniciales = findViewById(R.id.txtInicialesPerfil)
        txtNombre = findViewById(R.id.txtNombrePerfil)
        txtCorreo = findViewById(R.id.txtCorreoPerfil)
        cardPedidoActivo = findViewById(R.id.cardPedidoActivoPerfil)
        txtTituloPedidoActivo = findViewById(R.id.txtTituloPedidoActivoPerfil)
        txtSubtituloPedidoActivo = findViewById(R.id.txtSubPedidoActivoPerfil)
        txtBadgePedidosActivos = findViewById(R.id.txtBadgeActivosPerfil)
        txtFavoritosMenu = findViewById(R.id.txtFavoritosMenuPerfil)

        presenter = clsPerfilPresentador(this)

        cardPedidoActivo.setOnClickListener {
            startActivity(Intent(this, MisPedidosActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.menuMisPedidos).setOnClickListener {
            startActivity(Intent(this, MisPedidosActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.menuDireccionesEnvio).setOnClickListener {
            startActivity(Intent(this, EditarPerfilActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.menuCerrarSesion).setOnClickListener {
            presenter.cerrarSesion()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.cargarPerfil()
        BottomNavHelper.configurar(this, BottomNavHelper.Tab.PERFIL)
    }

    fun mostrarDatosCliente(cliente: dataCliente) {
        txtIniciales.text = cliente.iniciales()
        txtNombre.text = cliente.nombreCompleto()
        txtCorreo.text = cliente.correo
        txtBadgePedidosActivos.text = "${cliente.pedidosActivos} activo"
        txtFavoritosMenu.text = "Favoritos (${cliente.favoritosTotal})"
    }

    fun mostrarPedidoActivo(pedido: dataPedido?) {
        if (pedido != null) {
            cardPedidoActivo.visibility = View.VISIBLE
            txtTituloPedidoActivo.text = "Pedido #${pedido.folio} (${pedido.estado})"
            txtSubtituloPedidoActivo.text = "${pedido.direccionEnvio} • ${pedido.totalArticulos} artículos"
        } else {
            cardPedidoActivo.visibility = View.GONE
        }
    }

    fun navegarALogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
