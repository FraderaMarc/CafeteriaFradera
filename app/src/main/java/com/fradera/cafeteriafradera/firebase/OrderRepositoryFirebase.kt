package com.fradera.cafeteriafradera.firebase.orders

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class OrderRepositoryFirebase(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun ordersRef(uid: String) =
        db.collection("users").document(uid).collection("orders")

    suspend fun getOrders(uid: String): List<OrderDto> {
        val snap = ordersRef(uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()

        return snap.documents.mapNotNull { it.toObject(OrderDto::class.java) }
    }
}
