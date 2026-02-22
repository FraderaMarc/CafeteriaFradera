package com.fradera.cafeteriafradera.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenjarsDAO {

    @Query("SELECT * FROM menjars")
    suspend fun getMenjars(): List<Menjars>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(menjars: List<Menjars>)
}
