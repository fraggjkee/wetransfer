package com.fraggjkee.wetransfer.data

import com.fraggjkee.wetransfer.domain.Room

interface RoomsRepository {
    suspend fun getRooms(): List<Room>
    suspend fun bookRoom(room: Room)
}