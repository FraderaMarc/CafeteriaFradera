package com.fradera.cafeteriafradera.ui.historial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.R
import com.fradera.cafeteriafradera.firebase.orders.OrderDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistorialAdapter(
    private val onClick: (OrderDto) -> Unit
) : RecyclerView.Adapter<HistorialAdapter.VH>() {

    private val items = mutableListOf<OrderDto>()
    private val df = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    fun submitList(newItems: List<OrderDto>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial_order, parent, false)
        return VH(v, onClick, df)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class VH(
        itemView: View,
        private val onClick: (OrderDto) -> Unit,
        private val df: SimpleDateFormat
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvId: TextView = itemView.findViewById(R.id.tvOrderId)
        private val tvDate: TextView = itemView.findViewById(R.id.tvOrderDate)
        private val tvTotal: TextView = itemView.findViewById(R.id.tvOrderTotal)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)

        private var current: OrderDto? = null

        init {
            itemView.setOnClickListener {
                current?.let(onClick)
            }
        }

        fun bind(order: OrderDto) {
            current = order
            tvId.text = "Comanda: ${order.id.take(8)}"
            val millis = order.createdAt?.toDate()?.time ?: 0L
            tvDate.text = if (millis == 0L) "Fecha: -"
            else "Fecha: ${df.format(Date(millis))}"
            tvTotal.text = "Total: %.2f €".format(order.total)
            tvStatus.text = "Estado: ${order.status}"
        }
    }
}
