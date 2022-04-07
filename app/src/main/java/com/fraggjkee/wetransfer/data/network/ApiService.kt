package com.fraggjkee.wetransfer.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface ApiService {
    suspend fun getRooms(): GetRoomsResponse
    suspend fun bookRoom(roomId: String)
}

class KtorApiService @Inject constructor(
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
) : ApiService {

    override suspend fun getRooms(): GetRoomsResponse = withContext(dispatcher) {
        val response: HttpResponse = httpClient.get(PATH_ROOMS)
        val bodyJson = response.readText().removeLeadingSpaceIfNeeded()
        Json.decodeFromString(bodyJson)
    }

    override suspend fun bookRoom(roomId: String): Unit = withContext(dispatcher) {
        httpClient.get(PATH_BOOK_ROOM)
    }

    private companion object {
        const val PATH_ROOMS = "/rooms.json"
        const val PATH_BOOK_ROOM = "/bookRoom.json"
    }
}

private const val ZERO_WIDTH_SPACE = '\uFEFF'

// TODO Looks like /rooms.json returns JSON that starts from a "zero-width whitespace" and
//  kotlinx.serialization has some troubles with it, using this as a quick work-around.
//  Ideally, this should be done in a more general way, something like OkHttp Interceptor
//  to handle all similar cases automatically
private fun String.removeLeadingSpaceIfNeeded(): String {
    return if (startsWith(ZERO_WIDTH_SPACE)) {
        substring(1, length)
    } else this
}