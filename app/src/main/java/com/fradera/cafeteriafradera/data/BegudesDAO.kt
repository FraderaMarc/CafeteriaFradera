package com.fradera.cafeteriafradera.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BegudesDAO {
    @Query("SELECT * FROM begudes")
    suspend fun getBegudes(): List<Begudes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(begudes: List<Begudes>)}