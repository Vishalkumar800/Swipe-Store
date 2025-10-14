package com.rach.swipestore.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entity)

    @Query("SELECT * FROM productInfo")
    fun getAllInsert(): Flow<List<Entity>>

    @Delete
    suspend fun delete(entity: Entity)

}