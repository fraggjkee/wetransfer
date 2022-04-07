package com.fraggjkee.wetransfer.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRoomsResponse(
    @SerialName("rooms") val rooms: List<RoomDto>
) {
    @Serializable
    data class RoomDto(
        @SerialName("name") val name: String,
        @SerialName("spots") val spots: Int,
        @SerialName("thumbnail") val thumbnail: String
    )
}