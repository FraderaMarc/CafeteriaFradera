package com.fradera.cafeteriafradera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.Adapters.AdapterPagament
import com.fradera.cafeteriafradera.ViewModels.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PagamentFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: AdapterPagament

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_pagament, container, false)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerPagament)
        val btnPagar = view.findViewById<Button>(R.id.btnPagar) // <-- si tu id es otro, cámbialo

        adapter = AdapterPagament(mutableListOf()) { item ->
            sharedViewModel.removeItem(item)
        }

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        sharedViewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.update(items.toMutableList())
            tvTotal.text = "Total: ${sharedViewModel.getTotal()} €"
        }

        btnPagar.setOnClickListener { guardarPedido() }

        return view
    }

    private fun guardarPedido() {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(requireContext(), "Debes iniciar sesión para pagar.", Toast.LENGTH_SHORT).show()
            return
        }

        val items = sharedViewModel.items.value ?: mutableListOf()
        if (items.isEmpty()) {
            Toast.makeText(requireContext(), "No hay productos para pagar.", Toast.LENGTH_SHORT).show()
            return
        }

        val total = sharedViewModel.getTotal()
        val now = System.currentTimeMillis()

        // Ajusta campos según tu ProducteCarrito real:
        val itemsToSave = items.map { p ->
            hashMapOf(
                "nom" to p.nom,     // si tu campo se llama distinto, cámbialo aquí
                "preu" to p.preu
            )
        }

        val pedido = hashMapOf(
            "uid" to user.uid,
            "createdAtMillis" to now,
            "total" to total,
            "status" to "Pagado",
            "items" to itemsToSave
        )

        db.collection("orders")
            .add(pedido)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Pedido guardado.", Toast.LENGTH_SHORT).show()
                sharedViewModel.clearItems()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error guardando pedido: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}