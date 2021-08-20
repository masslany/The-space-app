package com.masslany.thespaceapp.data.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.masslany.thespaceapp.data.local.cache.dao.LaunchesDao
import com.masslany.thespaceapp.data.local.cache.dao.RoadsterDao
import com.masslany.thespaceapp.data.local.cache.dao.StarlinkDao
import com.masslany.thespaceapp.data.local.cache.entities.LaunchEntity
import com.masslany.thespaceapp.data.local.cache.entities.RoadsterEntity
import com.masslany.thespaceapp.data.local.cache.entities.StarlinkEntity

@Database(
    entities = [StarlinkEntity::class, LaunchEntity::class, RoadsterEntity::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun starlinkDao(): StarlinkDao
    abstract fun launchesDao(): LaunchesDao
    abstract fun roadsterDao(): RoadsterDao
}