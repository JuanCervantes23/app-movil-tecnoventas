package com.example.app_movil_tecnoventas.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_movil_tecnoventas.Modelo.ApiResponse
import com.example.app_movil_tecnoventas.Modelo.RetrofitCliente
import com.example.app_movil_tecnoventas.Modelo.dataCliente
import com.example.app_movil_tecnoventas.Modelo.dataPedido
import com.example.app_movil_tecnoventas.R
import java.text.NumberFormat
import java.util.Locale

class PedidoAdaptador(
    private var lista: List<dataPedido>
) : RecyclerView.Adapter<PedidoAdaptador.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFolio: TextView = view.findViewById(R.id.txtFolioPedidoItem)
        val txtEstado: TextView = view.findViewById(R.id.txtEstadoPedidoItem)
        val txtFecha: TextView = view.findViewById(R.id.txtFechaPedidoItem)
        val txtDetalle: TextView = view.findViewById(R.id.txtDetallePedidoItem)
        val txtTotal: TextView = view.findViewById(R.id.txtTotalPedidoItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        val formato = NumberFormat.getCurrencyInstance(Locale("es", "MX"))

        holder.txtFolio.text = "Pedido #${item.folio}"
        holder.txtEstado.text = item.estado
        holder.txtFecha.text = "${item.fecha} • ${item.metodoPago}"
        val nombres = item.items.joinToString(", ") { "${it.nombre} (x${it.cantidad})" }
        holder.txtDetalle.text = nombres.ifEmpty { "${item.totalArticulos} artículos" }
        holder.txtTotal.text = "${formato.format(item.total)} MXN"
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<dataPedido>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    suspend fun obtenerPerfil(idCliente: Int): dataCliente? {
        val respuesta = RetrofitCliente.apiService.obtenerPerfil(idCliente)
        return if (respuesta.status) respuesta.data else null
    }

    suspend fun actualizarPerfil(request: PerfilUpdateRequest): ApiResponse<dataCliente> {
        return RetrofitCliente.apiService.actualizarPerfil(request)
    }
}
