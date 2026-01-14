package com.fradera.cafeteriafradera.data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList


@Entity(tableName = "begudes")
data class Begudes(
    @PrimaryKey(autoGenerate = false)
    val idBeguda: Int,
    val nom: String,
    val preu: Double,
    val tipus: String,
    val imageResId: Int
)




