package com.example.app_movil_tecnoventas.Adaptador

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_movil_tecnoventas.Modelo.dataProducto
import com.example.app_movil_tecnoventas.R
import java.util.Locale

class ProductoGridAdaptador(
    private var lista: List<dataProducto>,
    private val onClickProducto: (dataProducto) -> Unit
) : RecyclerView.Adapter<ProductoGridAdaptador.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProducto: ImageView = view.findViewById(R.id.ivProductoGrid)
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoriaGrid)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreGrid)
        val tvStock: TextView = view.findViewById(R.id.tvStockGrid)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioGrid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]

        holder.tvCategoria.text = if (producto.marca.isNotBlank()) {
            "${producto.categoria} • ${producto.marca}"
        } else {
            producto.categoria
        }
        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = String.format(Locale.US, "$%,.2f", producto.precio)

        if (producto.stock > 0) {
            holder.tvStock.text = "Disponibles: ${producto.stock}"
            holder.tvStock.setTextColor(Color.parseColor("#2E7D32"))
        } else {
            holder.tvStock.text = "Agotado"
            holder.tvStock.setTextColor(Color.parseColor("#C62828"))
        }

        val urlImagen = if (producto.imagen.startsWith("http")) {
            producto.imagen
        } else {
            "https://equipo4.grupoc.io/uploads/img/${producto.imagen}"
        }

        Glide.with(holder.itemView.context)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.ivProducto)

        holder.itemView.setOnClickListener {
            onClickProducto(producto)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<dataProducto>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}