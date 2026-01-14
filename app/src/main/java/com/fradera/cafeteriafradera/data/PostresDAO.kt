package com.fradera.cafeteriafradera.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostresDAO {
    @Query("SELECT * FROM postres")
    suspend fun getPostres(): List<Postres>

    @Insert
    suspend fun insertAll(listOf: kotlin.collections.List<com.fradera.cafeteriafradera.data.Postres>)
}