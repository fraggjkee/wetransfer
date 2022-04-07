package com.fraggjkee.wetransfer.data.impl

import com.fraggjkee.wetransfer.data.RoomsLocalDataSource
import com.fraggjkee.wetransfer.data.persistence.RoomDao
import com.fraggjkee.wetransfer.data.persistence.RoomEntity
import com.fraggjkee.wetransfer.domain.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomsLocalDataSourceImpl @Inject constructor(
    private val roomDao: RoomDao,
    private val dispatcher: CoroutineDispatcher
) : RoomsLocalDataSource {

    override suspend fun persist(rooms: List<Room>) = withContext(dispatcher) {
        val entities = rooms.map { room -> room.toEntity() }.toTypedArray()
        roomDao.insertAll(*entities)
    }

    override suspend fun clear() = withContext(dispatcher) {
        roomDao.deleteAll()
    }

    override suspend fun getRooms(): List<Room> = withContext(dispatcher) {
        roomDao.getAll().map { entity -> entity.toDomain() }
    }
}

private fun Room.toEntity(): RoomEntity {
    return RoomEntity(
        name = this.name,
        spots = this.spotsCount,
        thumbnail = this.thumbnailUrl
    )
}

private fun RoomEntity.toDomain(): Room {
    return Room(
        name = this.name,
        spotsCount = this.spots,
        thumbnailUrl = this.thumbnail
    )
}
