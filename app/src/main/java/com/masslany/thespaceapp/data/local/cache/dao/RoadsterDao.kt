package com.masslany.thespaceapp.data.local.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masslany.thespaceapp.data.local.cache.entities.RoadsterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoadsterDao {

    @Query("SELECT * FROM roadster")
    fun getRoadster(): Flow<RoadsterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoadster(roadster: RoadsterEntity)

    @Query("DELETE FROM roadster")
    suspend fun deleteRoadster()
}