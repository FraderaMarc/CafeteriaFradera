package com.fradera.cafeteriafradera.data

fun Begudes.toProducteCarrito() = ProducteCarrito(
    id = this.idBeguda,
    nom = this.nom,
    preu = this.preu.toDouble(),
    tipus = TipusProducte.BEGUDA,
    imageResId = this.imageResId
)
fun Menjars.toProducteCarrito() = ProducteCarrito(
    id = this.id ?: 0,
    nom = this.nom,
    preu = this.preu,
    tipus = TipusProducte.MENJAR,
    imageResId = this.imageResId
)

fun Postres.toProducteCarrito() = ProducteCarrito(
    id = this.id ?: 0,
    nom = this.nom,
    preu = this.preu,
    tipus = TipusProducte.POSTRE,
    imageResId = this.imageResId
)