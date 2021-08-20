package com.masslany.thespaceapp.data.local.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masslany.thespaceapp.data.local.cache.entities.LaunchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchesDao {

    @Query("SELECT * FROM launches")
    fun getLaunches(): Flow<List<LaunchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchEntity>)

    @Query("DELETE FROM launches")
    suspend fun deleteLaunches()
}