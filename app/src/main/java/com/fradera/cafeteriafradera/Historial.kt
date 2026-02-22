package com.fradera.cafeteriafradera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fradera.cafeteriafradera.firebase.orders.OrderDto
import com.fradera.cafeteriafradera.firebase.orders.OrderRepositoryFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistorialViewModel(
    private val repo: OrderRepositoryFirebase = OrderRepositoryFirebase(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderDto>>(emptyList())
    val orders: StateFlow<List<OrderDto>> = _orders

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun load() {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            _error.value = "No hay usuario autenticado"
            _orders.value = emptyList()
            return
        }

        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            runCatching { repo.getOrders(uid) }
                .onSuccess { _orders.value = it }
                .onFailure { _error.value = it.message ?: "Error cargando historial" }

            _loading.value = false
        }
    }
}
