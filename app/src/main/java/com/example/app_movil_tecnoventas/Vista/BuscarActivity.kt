package com.example.app_movil_tecnoventas.Vista
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Adaptador.ProductoBusquedaAdaptador
import com.example.app_movil_tecnoventas.Modelo.dataProducto
import com.example.app_movil_tecnoventas.Presentador.BusquedaDetalleContract
import com.example.app_movil_tecnoventas.Presentador.clsBuscarPresentador
import com.example.app_movil_tecnoventas.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BuscarActivity : AppCompatActivity(), BusquedaDetalleContract.BuscarView {

    private lateinit var btnVolverBuscar: ImageButton
    private lateinit var etTerminoBusqueda: TextInputEditText
    private lateinit var btnEjecutarBusqueda: ImageButton
    private lateinit var tvEstadoBusqueda: TextView
    private lateinit var progressBuscar: ProgressBar
    private lateinit var rvResultadosBusqueda: RecyclerView

    private lateinit var adaptador: ProductoBusquedaAdaptador
    private lateinit var presenter: clsBuscarPresentador
    private var debounceJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        btnVolverBuscar = findViewById(R.id.btnVolverBuscar)
        etTerminoBusqueda = findViewById(R.id.etTerminoBusqueda)
        btnEjecutarBusqueda = findViewById(R.id.btnEjecutarBusqueda)
        tvEstadoBusqueda = findViewById(R.id.tvEstadoBusqueda)
        progressBuscar = findViewById(R.id.progressBuscar)
        rvResultadosBusqueda = findViewById(R.id.rvResultadosBusqueda)

        rvResultadosBusqueda.layoutManager = LinearLayoutManager(this)
        adaptador = ProductoBusquedaAdaptador(emptyList()) { producto ->
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("producto_id", producto.id)
            startActivity(intent)
        }
        rvResultadosBusqueda.adapter = adaptador

        presenter = clsBuscarPresentador(this)

        btnVolverBuscar.setOnClickListener { finish() }

        btnEjecutarBusqueda.setOnClickListener {
            debounceJob?.cancel()
            presenter.buscarProductos(etTerminoBusqueda.text.toString())
        }

        etTerminoBusqueda.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                debounceJob?.cancel()
                presenter.buscarProductos(etTerminoBusqueda.text.toString())
                true
            } else {
                false
            }
        }

        etTerminoBusqueda.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val texto = s?.toString()?.trim() ?: ""
                debounceJob?.cancel()
                if (texto.isEmpty()) {
                    adaptador.actualizarLista(emptyList())
                    tvEstadoBusqueda.text = "Escribe el nombre de un producto, categoría o marca"
                    return
                }
                debounceJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(400)
                    presenter.buscarProductos(texto)
                }
            }
        })

        etTerminoBusqueda.requestFocus()
    }

    override fun mostrarCargando(mostrar: Boolean) {
        progressBuscar.visibility = if (mostrar) View.VISIBLE else View.GONE
    }

    override fun mostrarResultados(productos: List<dataProducto>) {
        adaptador.actualizarLista(productos)
        tvEstadoBusqueda.text = if (productos.isEmpty()) {
            "Escribe el nombre de un producto, categoría o marca"
        } else {
            "${productos.size} resultado(s) encontrados"
        }
    }

    override fun mostrarSinResultados(termino: String) {
        adaptador.actualizarLista(emptyList())
        tvEstadoBusqueda.text = "No se encontraron productos para \"$termino\""
    }

    override fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        debounceJob?.cancel()
        presenter.onDestroy()
        super.onDestroy()
    }
}