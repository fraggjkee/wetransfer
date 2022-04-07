package com.fraggjkee.wetransfer.di

import com.fraggjkee.wetransfer.data.RoomsLocalDataSource
import com.fraggjkee.wetransfer.data.RoomsRemoteDataSource
import com.fraggjkee.wetransfer.data.RoomsRepository
import com.fraggjkee.wetransfer.data.impl.RoomsLocalDataSourceImpl
import com.fraggjkee.wetransfer.data.impl.RoomsRemoteDataSourceImpl
import com.fraggjkee.wetransfer.data.impl.RoomsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRoomsLocalDataSource(impl: RoomsLocalDataSourceImpl): RoomsLocalDataSource

    @Binds
    fun bindRoomsRemoteDataSource(impl: RoomsRemoteDataSourceImpl): RoomsRemoteDataSource

    @Binds
    fun bindRoomsRepository(impl: RoomsRepositoryImpl): RoomsRepository
}