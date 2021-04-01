package com.lakue.itunesgreendaysearch.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lakue.itunesgreendaysearch.model.Track

@Dao
interface TrackDao {
    @Query("SELECT * FROM track")
    fun getAll(): List<Track>

    @Query("SELECT trackId FROM Track")
    fun getTrackIds(): List<Int>

    @Insert
    fun insertTrack(vararg city: Track)

    @Delete
    fun deleteTrack(vararg city: Track)
}