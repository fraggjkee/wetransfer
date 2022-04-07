package com.fraggjkee.wetransfer.data.impl

import androidx.annotation.VisibleForTesting
import com.fraggjkee.wetransfer.data.RoomsRemoteDataSource
import com.fraggjkee.wetransfer.data.network.ApiService
import com.fraggjkee.wetransfer.data.network.GetRoomsResponse
import com.fraggjkee.wetransfer.domain.Room
import javax.inject.Inject

class RoomsRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RoomsRemoteDataSource {

    override suspend fun getRooms(): List<Room> {
        return apiService.getRooms()
            .rooms
            .map { dto -> dto.toDomain() }
    }
}

@VisibleForTesting
fun GetRoomsResponse.RoomDto.toDomain(): Room {
    return Room(
        name = this.name,
        spotsCount = this.spots,
        thumbnailUrl = this.thumbnail
    )
}