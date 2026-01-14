package com.fradera.cafeteriafradera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fradera.cafeteriafradera.Adapters.AdapterPostres
import com.fradera.cafeteriafradera.ViewModels.SharedViewModel
import com.fradera.cafeteriafradera.data.dataBase
import com.fradera.cafeteriafradera.data.toProducteCarrito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class PostresFragment : Fragment() {
    private lateinit var adapter: AdapterPostres
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_postres, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterPostres(mutableListOf()) { postre ->
            sharedViewModel.addItem(postre.toProducteCarrito())
        }

        recyclerView.adapter = adapter

        loadPostresFromDB()

        return view
    }

    private fun loadPostresFromDB() {
        val db = dataBase.getDatabase(requireContext())
        val dao = db.postraDao()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val postres = dao.getPostres()
            withContext(Dispatchers.Main) {
                adapter.updateList(postres)
            }
        }
    }
}