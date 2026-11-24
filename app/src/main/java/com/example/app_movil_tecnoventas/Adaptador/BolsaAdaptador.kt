package com.example.app_movil_tecnoventas.Adaptador

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageButton
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.app_movil_tecnoventas.Modelo.dataItemBolsa
    import com.example.app_movil_tecnoventas.R
    import java.text.NumberFormat
    import java.util.Locale

    class BolsaAdaptador(
        private var lista: List<dataItemBolsa>,
        private val alCambiarCantidad: (String, Int) -> Unit,
        private val alEliminar: (String) -> Unit
    ) : RecyclerView.Adapter<BolsaAdaptador.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imgProducto: ImageView = view.findViewById(R.id.imgProdBolsa)
            val txtNombre: TextView = view.findViewById(R.id.txtNombreBolsa)
            val txtMarca: TextView = view.findViewById(R.id.txtMarcaBolsa)
            val btnEliminar: ImageButton = view.findViewById(R.id.btnEliminarBolsa)
            val btnMenos: TextView = view.findViewById(R.id.btnMenosBolsa)
            val txtCantidad: TextView = view.findViewById(R.id.txtCantidadBolsa)
            val btnMas: TextView = view.findViewById(R.id.btnMasBolsa)
            val txtPrecio: TextView = view.findViewById(R.id.txtPrecioBolsa)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_bolsa, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = lista[position]
            val formato = NumberFormat.getCurrencyInstance(Locale("es", "MX"))

            holder.txtNombre.text = item.nombre
            holder.txtMarca.text = "${item.marca} • Stock: ${item.stock}"
            holder.txtCantidad.text = item.cantidad.toString()
            holder.txtPrecio.text = formato.format(item.subtotal())

            Glide.with(holder.itemView.context)
                .load(item.imagen)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .into(holder.imgProducto)

            holder.btnMenos.setOnClickListener {
                alCambiarCantidad(item.noSerie, item.cantidad - 1)
            }
            holder.btnMas.setOnClickListener {
                if (item.cantidad < item.stock) {
                    alCambiarCantidad(item.noSerie, item.cantidad + 1)
                }
            }
            holder.btnEliminar.setOnClickListener {
                alEliminar(item.noSerie)
            }
        }

        override fun getItemCount(): Int = lista.size

        fun actualizarLista(nuevaLista: List<dataItemBolsa>) {
            lista = nuevaLista
            notifyDataSetChanged()
        }

    }