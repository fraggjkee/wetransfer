package com.fraggjkee.wetransfer.data.impl

import com.fraggjkee.wetransfer.data.RoomsLocalDataSource
import com.fraggjkee.wetransfer.domain.Room
import io.mockk.spyk

class FakeLocalDataSource(
    private var rooms: List<Room>
) : RoomsLocalDataSource {

    override suspend fun persist(rooms: List<Room>) {
        this.rooms = rooms
    }

    override suspend fun clear() {
        this.rooms = emptyList()
    }

    override suspend fun getRooms(): List<Room> = rooms
}

fun EmptyLocalDataSource(): RoomsLocalDataSource {
    return FakeLocalDataSource(rooms = emptyList())
}

fun NonEmptyLocalDataSource(): RoomsLocalDataSource {
    return FakeLocalDataSource(
        rooms = listOf(
            Room("first", 10, ""),
            Room("second", 50, ""),
            Room("third", 0, "")
        )
    )
}

fun RoomsLocalDataSource.asSpyk() = spyk(this)