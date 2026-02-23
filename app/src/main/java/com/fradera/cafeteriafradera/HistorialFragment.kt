package com.fradera.cafeteriafradera

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.ui.historial.AdapterHistorial

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistorialFragment : Fragment(R.layout.fragment_historial) {

    private val vm: HistorialViewModel by viewModels()

    private lateinit var rv: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var tvError: TextView
    private lateinit var pb: ProgressBar

    private lateinit var adapter: AdapterHistorial

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.rvHistorial)
        tvEmpty = view.findViewById(R.id.tvEmpty)
        tvError = view.findViewById(R.id.tvError)
        pb = view.findViewById(R.id.pbLoading)

        adapter = AdapterHistorial { }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            vm.orders.collectLatest { list ->
                adapter.submitList(list)
                tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.error.collectLatest { err ->
                tvError.text = err ?: ""
                tvError.visibility = if (err.isNullOrBlank()) View.GONE else View.VISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.loading.collectLatest { loading ->
                pb.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }

        vm.load()
    }
}
