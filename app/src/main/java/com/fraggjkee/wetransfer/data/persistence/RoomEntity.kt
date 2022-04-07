package com.fraggjkee.wetransfer.data.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "spots") val spots: Int,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)