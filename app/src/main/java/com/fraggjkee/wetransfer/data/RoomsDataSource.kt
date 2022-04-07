package com.fraggjkee.wetransfer.data

import com.fraggjkee.wetransfer.domain.Room

interface RoomsDataSource {
    suspend fun getRooms(): List<Room>
}

interface RoomsRemoteDataSource : RoomsDataSource

interface RoomsLocalDataSource : RoomsDataSource {
    suspend fun persist(rooms: List<Room>)
    suspend fun clear()
}