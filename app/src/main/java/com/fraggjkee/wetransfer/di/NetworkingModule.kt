package com.fraggjkee.wetransfer.di

import android.util.Log
import com.fraggjkee.wetransfer.BuildConfig
import com.fraggjkee.wetransfer.data.network.ApiService
import com.fraggjkee.wetransfer.data.network.KtorApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import javax.inject.Singleton

private const val BASE_URL = "wetransfer.github.io"

@Module(includes = [ApiModule::class])
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                }
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = AndroidLogger()
                level = LogLevel.ALL
            }
        }
    }
}

@Module
@DisableInstallInCheck
private interface ApiModule {

    @Binds
    fun bindApiService(impl: KtorApiService): ApiService
}

// TODO A very simple implementation. In production, I'd use something like Timber
private class AndroidLogger : Logger {

    override fun log(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

    private companion object {
        const val TAG = "Networking"
    }
}