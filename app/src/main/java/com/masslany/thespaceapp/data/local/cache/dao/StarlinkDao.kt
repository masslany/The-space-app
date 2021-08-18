package com.masslany.thespaceapp.data.local.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masslany.thespaceapp.data.local.cache.entities.StarlinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StarlinkDao {

    @Query("SELECT * FROM starlinks")
    fun getStarlinks(): Flow<List<StarlinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStarlinks(starlinks: List<StarlinkEntity>)

    @Query("DELETE FROM starlinks")
    suspend fun deleteStarlinks()
}