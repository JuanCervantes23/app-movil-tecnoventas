package com.example.app_movil_tecnoventas.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_movil_tecnoventas.Modelo.dataPreguntaSeguridad
import com.example.app_movil_tecnoventas.Presentador.clsRegistroPresentador
import com.example.app_movil_tecnoventas.R

class RegistroActivity : AppCompatActivity() {
    private lateinit var presenter: clsRegistroPresentador
    private lateinit var btnAtras: ImageButton
    private lateinit var etNombre: EditText
    private lateinit var etApPaterno: EditText
    private lateinit var etApMaterno: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etPassword: EditText
    private lateinit var spnPregunta: Spinner
    private lateinit var etRespuesta: EditText
    private lateinit var chkTerminos: CheckBox
    private lateinit var btnRegistrarse: Button
    private lateinit var txtIrLogin: TextView
    private lateinit var pbRegistro: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        btnAtras = findViewById(R.id.btnAtrasRegistro)
        etNombre = findViewById(R.id.etNombreReg)
        etApPaterno = findViewById(R.id.etApPaternoReg)
        etApMaterno = findViewById(R.id.etApMaternoReg)
        etCorreo = findViewById(R.id.etCorreoReg)
        etTelefono = findViewById(R.id.etTelefonoReg)
        etPassword = findViewById(R.id.etPasswordReg)
        spnPregunta = findViewById(R.id.spnPreguntaSeguridad)
        etRespuesta = findViewById(R.id.etRespuestaSeguridad)
        chkTerminos = findViewById(R.id.chkTerminos)
        btnRegistrarse = findViewById(R.id.btnRegistrarCliente)
        txtIrLogin = findViewById(R.id.txtIrLogin)
        pbRegistro = findViewById(R.id.pbRegistro)

        presenter = clsRegistroPresentador(this)
        presenter.cargarPreguntasSeguridad()

        btnAtras.setOnClickListener { finish() }
        txtIrLogin.setOnClickListener { finish() }

        btnRegistrarse.setOnClickListener {
            presenter.registrar(
                nombre = etNombre.text.toString().trim(),
                apPaterno = etApPaterno.text.toString().trim(),
                apMaterno = etApMaterno.text.toString().trim(),
                correo = etCorreo.text.toString().trim(),
                telefono = etTelefono.text.toString().trim(),
                password = etPassword.text.toString().trim(),
                posicionPregunta = spnPregunta.selectedItemPosition,
                respuestaSeguridad = etRespuesta.text.toString().trim(),
                aceptoTerminos = chkTerminos.isChecked
            )
        }
    }

    fun mostrarPreguntas(preguntas: List<dataPreguntaSeguridad>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, preguntas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPregunta.adapter = adapter
    }

    fun mostrarCargando(cargando: Boolean) {
        pbRegistro.visibility = if (cargando) View.VISIBLE else View.GONE
        btnRegistrarse.isEnabled = !cargando
        btnRegistrarse.text = if (cargando) "Creando cuenta..." else "Registrarse"
    }

    fun registroExitoso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
}