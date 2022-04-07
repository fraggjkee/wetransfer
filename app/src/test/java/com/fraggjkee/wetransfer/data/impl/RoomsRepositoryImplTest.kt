package com.fraggjkee.wetransfer.data.impl

import com.fraggjkee.wetransfer.data.network.ApiService
import com.fraggjkee.wetransfer.domain.Room
import io.mockk.Called
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

// TODO just a couple of example unit tests, they not provide a 100% coverage. In production,
//  I'd write more for the repo + some other classes like ViewModels should also be covered
//  with tests.
class RoomsRepositoryImplTest {

    private val apiServiceMock: ApiService = mockk(relaxed = true)

    @Test
    fun `getRooms() fetches remote data when local source is empty`() = runBlocking {
        // GIVEN
        val localDataSourceSpyk = EmptyLocalDataSource().asSpyk()
        val remoteDataSourceSpyk = FakeRemoteDataSource(emptyList()).asSpyk()
        val repo = RoomsRepositoryImpl(
            localDataSource = localDataSourceSpyk,
            remoteDataSource = remoteDataSourceSpyk,
            apiService = apiServiceMock
        )

        // WHEN
        repo.getRooms()

        // THEN
        coVerify(atLeast = 1) { localDataSourceSpyk.getRooms() }
        coVerify(exactly = 1) { remoteDataSourceSpyk.getRooms() }
    }

    @Test
    fun `getRooms() returns local data when it is available`() = runBlocking {
        // GIVEN
        val localDataSourceSpyk = NonEmptyLocalDataSource().asSpyk()
        val remoteDataSourceSpyk = FakeRemoteDataSource(emptyList()).asSpyk()
        val repo = RoomsRepositoryImpl(
            localDataSource = localDataSourceSpyk,
            remoteDataSource = remoteDataSourceSpyk,
            apiService = apiServiceMock
        )

        // WHEN
        repo.getRooms()

        // THEN
        coVerify(exactly = 1) { localDataSourceSpyk.getRooms() }
        coVerify { remoteDataSourceSpyk wasNot Called }
    }

    @Test
    fun `getRooms() persist remote data in local store`() = runBlocking {
        // GIVEN
        val rooms = listOf(
            Room("room", 0, "")
        )
        val localDataSourceSpyk = EmptyLocalDataSource().asSpyk()
        val remoteDataSource = FakeRemoteDataSource(rooms)
        val repo = RoomsRepositoryImpl(
            localDataSource = localDataSourceSpyk,
            remoteDataSource = remoteDataSource,
            apiService = apiServiceMock
        )

        // WHEN
        repo.getRooms()

        // THEN
        coVerify(exactly = 1) { localDataSourceSpyk.persist(rooms) }
    }

    @Test
    fun `bookRoom() delegates its job to Api Service`() = runBlocking {
        // GIVEN
        val room = Room("first", 50, "")
        val repo = RoomsRepositoryImpl(
            localDataSource = EmptyLocalDataSource(),
            remoteDataSource = EmptyRemoteDataSource(),
            apiService = apiServiceMock
        )

        // WHEN
        repo.bookRoom(room)

        // THEN
        coVerify { repo.bookRoom(room) }
    }
}