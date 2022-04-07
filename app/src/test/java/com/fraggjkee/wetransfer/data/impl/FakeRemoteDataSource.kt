package com.fraggjkee.wetransfer.data.impl

import com.fraggjkee.wetransfer.data.RoomsRemoteDataSource
import com.fraggjkee.wetransfer.domain.Room
import io.mockk.spyk

class FakeRemoteDataSource(private val rooms: List<Room>) : RoomsRemoteDataSource {
    override suspend fun getRooms(): List<Room> = rooms
}

fun EmptyRemoteDataSource(): RoomsRemoteDataSource {
    return FakeRemoteDataSource(rooms = emptyList())
}

fun RoomsRemoteDataSource.asSpyk() = spyk(this)