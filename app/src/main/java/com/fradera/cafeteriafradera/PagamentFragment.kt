package com.fradera.cafeteriafradera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.Adapters.AdapterPagament
import com.fradera.cafeteriafradera.ViewModels.SharedViewModel

class PagamentFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: AdapterPagament

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_pagament, container, false)

        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerPagament)

        adapter = AdapterPagament(mutableListOf()) { item ->
            sharedViewModel.removeItem(item)
        }

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        sharedViewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.update(items.toMutableList())
            tvTotal.text = "Total: ${sharedViewModel.getTotal()} €"
        }

        return view
    }
}
