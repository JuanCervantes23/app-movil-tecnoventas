package com.example.app_movil_tecnoventas.Adaptador

import android.graphics.Paint
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

class ProductoHorizontalAdaptador(
    private var lista: List<dataProducto>,
    private val onClickProducto: (dataProducto) -> Unit
) : RecyclerView.Adapter<ProductoHorizontalAdaptador.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProducto: ImageView = view.findViewById(R.id.ivProductoHoriz)
        val tvMarca: TextView = view.findViewById(R.id.tvMarcaHoriz)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreHoriz)
        val tvPrecioAnterior: TextView = view.findViewById(R.id.tvPrecioAnteriorHoriz)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioHoriz)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_horizontal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]

        holder.tvMarca.text = producto.marca.ifBlank { producto.categoria }
        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = String.format(Locale.US, "$%,.2f", producto.precio)

        if (producto.precioAnterior != null && producto.precioAnterior > producto.precio) {
            holder.tvPrecioAnterior.visibility = View.VISIBLE
            holder.tvPrecioAnterior.text = String.format(Locale.US, "$%,.2f", producto.precioAnterior)
            holder.tvPrecioAnterior.paintFlags =
                holder.tvPrecioAnterior.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.tvPrecioAnterior.visibility = View.GONE
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