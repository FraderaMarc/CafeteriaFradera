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
import com.fradera.cafeteriafradera.Adapters.AdapterMenjars
import com.fradera.cafeteriafradera.ViewModels.SharedViewModel
import com.fradera.cafeteriafradera.data.dataBase
import com.fradera.cafeteriafradera.data.toProducteCarrito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenjarsFragment : Fragment() {
    private lateinit var adapter: AdapterMenjars
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_menjars, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AdapterMenjars(mutableListOf()) { menjar ->
            sharedViewModel.addItem(menjar.toProducteCarrito())
        }

        recyclerView.adapter = adapter

        loadMenjarsFromDB()

        return view
    }

    private fun loadMenjarsFromDB() {
        val db = dataBase.getDatabase(requireContext())
        val dao = db.menjarDao()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val menjars = dao.getMenjars()
            withContext(Dispatchers.Main) {
                adapter.updateList(menjars)
            }
        }
    }
}