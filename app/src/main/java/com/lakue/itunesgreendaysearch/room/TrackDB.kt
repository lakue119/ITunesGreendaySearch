package com.lakue.itunesgreendaysearch.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lakue.itunesgreendaysearch.model.Track

@Database(entities = [Track::class], version = 1, exportSchema = false)
abstract class TrackDB : RoomDatabase() {
    abstract val trackDao: TrackDao

    companion object {

        @Volatile
        private var INSTANCE: TrackDB? = null

        fun getInstance(context: Context): TrackDB {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TrackDB::class.java,
                        "track_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance

                }

                return instance
            }
        }
    }
}