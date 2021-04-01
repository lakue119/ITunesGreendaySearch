package com.lakue.itunesgreendaysearch.di

import android.content.Context
import com.lakue.itunesgreendaysearch.repository.FavoriteTrackRepository
import com.lakue.itunesgreendaysearch.room.TrackDB
import com.lakue.itunesgreendaysearch.room.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideCityDao(@ApplicationContext appContext: Context): TrackDao{
        return TrackDB.getInstance(appContext).trackDao
    }

    @Provides
    fun provideCityDBRepository(cityDao: TrackDao) = FavoriteTrackRepository(cityDao)

}