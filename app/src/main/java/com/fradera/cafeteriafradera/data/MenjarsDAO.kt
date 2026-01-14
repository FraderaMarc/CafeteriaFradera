package com.fradera.cafeteriafradera.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenjarsDAO {
    @Query("SELECT * FROM menjars")
    suspend fun getMenjars(): List<Menjars>

    @Insert
    suspend fun insertAll(listOf: kotlin.collections.List<com.fradera.cafeteriafradera.data.Menjars>)
}