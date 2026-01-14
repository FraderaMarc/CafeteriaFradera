package com.fradera.cafeteriafradera.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.R
import com.fradera.cafeteriafradera.data.Begudes

class AdapterBegudes(
    private val items: MutableList<Begudes>,
    private val onAddClick: (Begudes) -> Unit
) : RecyclerView.Adapter<AdapterBegudes.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.imageView.setImageResource(item.imageResId)
        holder.nomText.text = item.nom
        holder.preuText.text = "${item.preu} €"
        holder.tipusText.text = item.tipus

        holder.btnAdd.setOnClickListener {
            onAddClick(item)
        }
    }

    override fun getItemCount() = items.size

    fun updateList(newList: List<Begudes>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageview)
        val nomText: TextView = view.findViewById(R.id.textView)
        val preuText: TextView = view.findViewById(R.id.tvPreu)
        val tipusText: TextView = view.findViewById(R.id.tvTipus)
        val btnAdd: Button = view.findViewById(R.id.btnAddCart)
    }
}
