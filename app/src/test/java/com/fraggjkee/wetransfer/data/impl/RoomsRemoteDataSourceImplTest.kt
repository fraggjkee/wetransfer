package com.fraggjkee.wetransfer.data.impl

import com.fraggjkee.wetransfer.data.network.ApiService
import com.fraggjkee.wetransfer.data.network.GetRoomsResponse
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

private val testGetRoomsResponse = GetRoomsResponse(
    rooms = listOf(
        GetRoomsResponse.RoomDto("1", 1, ""),
        GetRoomsResponse.RoomDto("2", 2, ""),
        GetRoomsResponse.RoomDto("3", 2, "")
    )
)

class RoomsRemoteDataSourceImplTest {

    @Test
    fun `getRooms() returns mapped list obtained from Api Service`() = runBlocking {
        // GIVEN
        val apiServiceMock: ApiService = mockk()
        coEvery { apiServiceMock.getRooms() } returns testGetRoomsResponse
        val dataSource = RoomsRemoteDataSourceImpl(apiServiceMock)
        val expected = testGetRoomsResponse.rooms.map { it.toDomain() }

        // WHEN
        val actual = dataSource.getRooms()

        // THEN
        actual
            .shouldNotBeEmpty()
            .shouldBe(expected)
    }
}