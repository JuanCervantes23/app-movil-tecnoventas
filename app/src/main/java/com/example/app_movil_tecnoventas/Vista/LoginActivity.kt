package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_movil_tecnoventas.Modelo.RetrofitCliente
import com.example.app_movil_tecnoventas.Presentador.clsLoginPresentador
import com.example.app_movil_tecnoventas.R

class LoginActivity : AppCompatActivity() {
    private lateinit var presenter: clsLoginPresentador
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var txtOlvidePassword: TextView
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnCrearCuenta: Button
    private lateinit var pbLogin: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        etCorreo = findViewById(R.id.etCorreoLogin)
        etPassword = findViewById(R.id.etPasswordLogin)
        txtOlvidePassword = findViewById(R.id.txtOlvidePassword)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnCrearCuenta = findViewById(R.id.btnIrCrearCuenta)
        pbLogin = findViewById(R.id.pbLogin)

        presenter = clsLoginPresentador(this)
        presenter.verificarSesionPrevia()

        btnIniciarSesion.setOnClickListener {
            presenter.iniciarSesion(
                etCorreo.text.toString().trim(),
                etPassword.text.toString().trim()
            )
        }

        btnCrearCuenta.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        txtOlvidePassword.setOnClickListener {
            val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitCliente.URL_RECUPERAR_PASSWORD))
            startActivity(intentWeb)
        }
    }

    fun mostrarCargando(cargando: Boolean) {
        pbLogin.visibility = if (cargando) View.VISIBLE else View.GONE
        btnIniciarSesion.isEnabled = !cargando
        btnIniciarSesion.text = if (cargando) "Iniciando sesión..." else "Iniciar sesión"
    }

    fun loginExitoso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        navegarAInicio()
    }

    fun navegarAInicio() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}