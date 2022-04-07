package com.fraggjkee.wetransfer.di

import android.content.Context
import androidx.room.Room
import com.fraggjkee.wetransfer.data.persistence.AppDatabase
import com.fraggjkee.wetransfer.data.persistence.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "wetransfer-db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRoomDao(db: AppDatabase): RoomDao {
        return db.roomDao()
    }
}

