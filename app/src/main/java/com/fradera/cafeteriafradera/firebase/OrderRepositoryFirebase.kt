package com.fradera.cafeteriafradera.firebase.orders

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class OrderRepositoryFirebase(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun getOrders(uid: String): List<OrderDto> {
        val snap = db.collection("orders")
            .whereEqualTo("uid", uid)
            .orderBy("createdAtMillis", Query.Direction.DESCENDING)
            .get()
            .await()

        return snap.documents.map { doc ->
            val total = doc.getDouble("total") ?: 0.0
            val status = doc.getString("status") ?: "Pagado"
            val createdAtMillis = doc.getLong("createdAtMillis") ?: 0L

            @Suppress("UNCHECKED_CAST")
            val items = (doc.get("items") as? List<Map<String, Any?>>) ?: emptyList()

            OrderDto(
                id = doc.id,
                uid = doc.getString("uid") ?: uid,
                total = total,
                status = status,
                createdAtMillis = createdAtMillis,
                items = items
            )
        }
    }
}