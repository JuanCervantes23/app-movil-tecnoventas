package com.example.app_movil_tecnoventas.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Modelo.dataMetodoPago
import com.example.app_movil_tecnoventas.R

class MetodoPagoAdaptador(
    private var lista: List<dataMetodoPago>,
    private val alSeleccionar: (dataMetodoPago) -> Unit
) : RecyclerView.Adapter<MetodoPagoAdaptador.ViewHolder>() {

    private var posicionSeleccionada = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contenedor: LinearLayout = view.findViewById(R.id.contenedorMetodoPago)
        val txtNombre: TextView = view.findViewById(R.id.txtNombreMetodoPago)
        val txtDesc: TextView = view.findViewById(R.id.txtDescMetodoPago)
        val rbSeleccion: RadioButton = view.findViewById(R.id.rbMetodoPago)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_metodo_pago, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        val seleccionado = position == posicionSeleccionada

        holder.txtNombre.text = item.nombre
        holder.txtDesc.text = item.descripcion.ifEmpty { "Pago seguro en TecnoVentas" }
        holder.rbSeleccion.isChecked = seleccionado

        holder.contenedor.setBackgroundResource(
            if (seleccionado) R.drawable.bg_pill_azul_suave else R.drawable.bg_boton_blanco_borde
        )
        holder.txtNombre.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                if (seleccionado) R.color.tv_azul_primario else R.color.tv_texto_principal
            )
        )

        holder.itemView.setOnClickListener {
            posicionSeleccionada = holder.adapterPosition
            notifyDataSetChanged()
            alSeleccionar(item)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<dataMetodoPago>) {
        lista = nuevaLista
        posicionSeleccionada = 0
        notifyDataSetChanged()
        if (lista.isNotEmpty()) alSeleccionar(lista[0])
    }
}