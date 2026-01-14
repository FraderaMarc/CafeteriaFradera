package com.fradera.cafeteriafradera.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.R
import com.fradera.cafeteriafradera.data.ProducteCarrito

class AdapterPagament(
    private val items: MutableList<ProducteCarrito>,
    private val onRemoveClick: (ProducteCarrito) -> Unit
) : RecyclerView.Adapter<AdapterPagament.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_pagament, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.imageView.setImageResource(item.imageResId)
        holder.nomText.text = item.nom
        holder.preuText.text = "${item.preu} €"
        holder.tipusText.text = item.tipus.name

        holder.btnRemove.setOnClickListener {
            onRemoveClick(item)
        }
    }

    override fun getItemCount() = items.size

    fun update(newList: MutableList<ProducteCarrito>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageview)
        val nomText: TextView = view.findViewById(R.id.textView)
        val preuText: TextView = view.findViewById(R.id.tvPreu)
        val tipusText: TextView = view.findViewById(R.id.tvTipus)
        val btnRemove: Button = view.findViewById(R.id.btnRemove)
    }
}
