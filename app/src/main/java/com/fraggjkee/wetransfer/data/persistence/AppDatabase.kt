package com.fraggjkee.wetransfer.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}