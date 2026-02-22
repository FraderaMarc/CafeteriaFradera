package com.fradera.cafeteriafradera.firebase.orders

import com.google.firebase.Timestamp

data class OrderItemDto(
    val plateId: Int = 0,
    val name: String = "",
    val qty: Int = 0,
    val price: Double = 0.0
)

data class OrderDto(
    val id: String = "",
    val createdAt: Timestamp? = null,
    val status: String = "created",
    val total: Double = 0.0,
    val items: List<OrderItemDto> = emptyList()
)