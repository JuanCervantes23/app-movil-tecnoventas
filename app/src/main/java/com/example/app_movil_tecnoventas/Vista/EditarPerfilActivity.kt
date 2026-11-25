package com.example.app_movil_tecnoventas.Vista

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_movil_tecnoventas.Modelo.ErrorParser
import com.example.app_movil_tecnoventas.Modelo.PerfilUpdateRequest
import com.example.app_movil_tecnoventas.Modelo.SessionManager
import com.example.app_movil_tecnoventas.Modelo.clsPerfilModelo
import com.example.app_movil_tecnoventas.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditarPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        val sessionManager = SessionManager(this)
        val cliente = sessionManager.obtenerCliente() ?: return
        val modelo = clsPerfilModelo()

        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasEditarPerfil)
        val etNombre = findViewById<EditText>(R.id.etNombreEdit)
        val etApPaterno = findViewById<EditText>(R.id.etApPaternoEdit)
        val etApMaterno = findViewById<EditText>(R.id.etApMaternoEdit)
        val etTelefono = findViewById<EditText>(R.id.etTelefonoEdit)
        val etCalle = findViewById<EditText>(R.id.etCalleEdit)
        val etNumExt = findViewById<EditText>(R.id.etNumExtEdit)
        val etColonia = findViewById<EditText>(R.id.etColoniaEdit)
        val etCP = findViewById<EditText>(R.id.etCPEdit)
        val etCiudad = findViewById<EditText>(R.id.etCiudadEdit)
        val etEstado = findViewById<EditText>(R.id.etEstadoEdit)
        val etReferencias = findViewById<EditText>(R.id.etReferenciasEdit)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPerfil)

        etNombre.setText(cliente.nombre)
        etApPaterno.setText(cliente.apellidoPaterno)
        etApMaterno.setText(cliente.apellidoMaterno)
        etTelefono.setText(cliente.telefono)
        etCalle.setText(cliente.calle)
        etNumExt.setText(cliente.numExterior)
        etColonia.setText(cliente.colonia)
        etCP.setText(cliente.codigoPostal)
        etCiudad.setText(cliente.ciudad)
        etEstado.setText(cliente.estadoRep)
        etReferencias.setText(cliente.referencias)

        btnAtras.setOnClickListener { finish() }

        btnGuardar.setOnClickListener {
            val req = PerfilUpdateRequest(
                idCliente = cliente.id,
                nombre = etNombre.text.toString().trim(),
                apellidoPaterno = etApPaterno.text.toString().trim(),
                apellidoMaterno = etApMaterno.text.toString().trim(),
                telefono = etTelefono.text.toString().trim(),
                calle = etCalle.text.toString().trim(),
                numExterior = etNumExt.text.toString().trim(),
                colonia = etColonia.text.toString().trim(),
                codigoPostal = etCP.text.toString().trim(),
                ciudad = etCiudad.text.toString().trim(),
                estadoRep = etEstado.text.toString().trim(),
                referencias = etReferencias.text.toString().trim()
            )
            btnGuardar.isEnabled = false
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val res = modelo.actualizarPerfil(req)
                    if (res.status && res.data != null) {
                        val actualizado = cliente.copy(
                            nombre = req.nombre,
                            apellidoPaterno = req.apellidoPaterno,
                            apellidoMaterno = req.apellidoMaterno,
                            telefono = req.telefono,
                            calle = req.calle,
                            numExterior = req.numExterior,
                            colonia = req.colonia,
                            codigoPostal = req.codigoPostal,
                            ciudad = req.ciudad,
                            estadoRep = req.estadoRep,
                            referencias = req.referencias,
                            direccionCompleta = res.data.direccionCompleta
                        )
                        sessionManager.guardarCliente(actualizado)
                        Toast.makeText(this@EditarPerfilActivity, res.message, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditarPerfilActivity, res.message, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@EditarPerfilActivity, ErrorParser.obtenerMensaje(e), Toast.LENGTH_LONG).show()
                } finally {
                    btnGuardar.isEnabled = true
                }
            }
        }
    }
}
