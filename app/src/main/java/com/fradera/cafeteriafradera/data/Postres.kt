package com.fradera.cafeteriafradera.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postres")
data class Postres(

    @ColumnInfo(name = "nom")
    var nom: String,
    @ColumnInfo(name = "preu")
    var preu: Double,
    @ColumnInfo(name = "tipus")
    var tipus: String,
    @ColumnInfo(name= "imatge")
    val imageResId: Int)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
