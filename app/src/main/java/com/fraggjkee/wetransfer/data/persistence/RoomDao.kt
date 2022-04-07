package com.fraggjkee.wetransfer.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {

    @Query("SELECT * FROM room")
    fun getAll(): List<RoomEntity>

    @Insert
    suspend fun insertAll(vararg users: RoomEntity)

    @Query("DELETE FROM room")
    suspend fun deleteAll()
}