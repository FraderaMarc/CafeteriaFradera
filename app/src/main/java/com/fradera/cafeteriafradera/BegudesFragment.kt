package com.fradera.cafeteriafradera

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.Adapters.AdapterBegudes
import com.fradera.cafeteriafradera.ViewModels.SharedViewModel
import com.fradera.cafeteriafradera.data.BegudesDAO
import com.fradera.cafeteriafradera.data.dataBase
import com.fradera.cafeteriafradera.data.toProducteCarrito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BegudesFragment : Fragment() {

    private lateinit var adapter: AdapterBegudes
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_begudes, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterBegudes(mutableListOf()) { beguda ->
            sharedViewModel.addItem(beguda.toProducteCarrito())
        }

        recyclerView.adapter = adapter

        loadBegudesFromDB()

        return view
    }

    private fun loadBegudesFromDB() {
        val db = dataBase.getDatabase(requireContext())
        val begudaDao = db.begudaDao()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val begudes = begudaDao.getBegudes()

            withContext(Dispatchers.Main) {
                adapter.updateList(begudes)
            }
        }
    }
}