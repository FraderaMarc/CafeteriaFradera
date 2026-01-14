package com.fradera.cafeteriafradera.data

    data class ProducteCarrito(
        val id: Int,
        val nom: String,
        val preu: Double,
        val tipus: TipusProducte,
        val imageResId: Int
    )

    enum class TipusProducte {
        BEGUDA, MENJAR, POSTRE
    }
