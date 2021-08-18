package com.masslany.thespaceapp.data.local.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.masslany.thespaceapp.data.local.cache.dao.StarlinkDao
import com.masslany.thespaceapp.data.local.cache.entities.StarlinkEntity

@Database(
    entities = [StarlinkEntity::class],
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun starlinkDao(): StarlinkDao
}