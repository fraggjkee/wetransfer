package com.fraggjkee.wetransfer.data.impl

import com.fraggjkee.wetransfer.data.RoomsLocalDataSource
import com.fraggjkee.wetransfer.data.RoomsRemoteDataSource
import com.fraggjkee.wetransfer.data.RoomsRepository
import com.fraggjkee.wetransfer.data.network.ApiService
import com.fraggjkee.wetransfer.domain.Room
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val localDataSource: RoomsLocalDataSource,
    private val remoteDataSource: RoomsRemoteDataSource,
    private val apiService: ApiService
) : RoomsRepository {

    override suspend fun getRooms(): List<Room> {
        // TODO This is a simplified implementation. It may be a good idea to introduce some rules
        //  for local cache eviction, we should also support something like "Force Refresh" to use
        //  with Pull-to-Refresh component in UI, etc.
        val localData = localDataSource.getRooms()
        return localData.ifEmpty {
            val remoteData = remoteDataSource.getRooms()
            localDataSource.persist(remoteData)
            localDataSource.getRooms()
        }
    }

    override suspend fun bookRoom(room: Room) {
        apiService.bookRoom(roomId = room.name)
    }
}