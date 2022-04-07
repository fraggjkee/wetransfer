package com.fraggjkee.wetransfer.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}