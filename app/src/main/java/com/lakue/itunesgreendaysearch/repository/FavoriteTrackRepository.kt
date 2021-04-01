package com.lakue.itunesgreendaysearch.repository

import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.room.TrackDao
import javax.inject.Inject

class FavoriteTrackRepository@Inject constructor(private val trackDao: TrackDao) {
    suspend fun insertTrack(roomCity: Track) = trackDao.insertTrack(roomCity)
    suspend fun fetchTracks() = trackDao.getAll()
    suspend fun fetchTrackIds() = trackDao.getTrackIds()
    suspend fun deleteTrack(roomCity:Track) = trackDao.deleteTrack(roomCity)
}