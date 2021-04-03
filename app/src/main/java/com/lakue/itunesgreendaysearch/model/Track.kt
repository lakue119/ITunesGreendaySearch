package com.lakue.itunesgreendaysearch.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Track(
    val artistId: Int = 0,
    val artistName: String = "",
    val artistViewUrl:  String = "",
    val artworkUrl100:  String = "",
    val artworkUrl30:  String = "",
    val artworkUrl60:  String = "",
    val collectionCensoredName:  String = "",
    val collectionExplicitness:  String = "",
    val collectionId:  Int = 0,
    val collectionName:  String = "",
    val collectionPrice:  Double = 0.0,
    val collectionViewUrl:  String = "",
    val country:  String = "",
    val currency:  String = "",
    val discCount:  Int = 0,
    val discNumber:  Int = 0,
    val isStreamable:  Boolean = false,
    val kind:  String = "",
    val previewUrl:  String = "",
    val primaryGenreName:  String = "",
    val releaseDate:  String = "",
    val trackCensoredName:  String = "",
    val trackCount:  Int = 0,
    val trackExplicitness:  String = "",
    @PrimaryKey(autoGenerate = true)
    val trackId:  Int = 0,
    val trackName:  String = "",
    val trackNumber:  Int = 0,
    val trackPrice:  Double = 0.0,
    val trackTimeMillis:  Int = 0,
    val trackViewUrl:  String = "",
    val wrapperType:  String = "",
    var favorite: Boolean = false
): Serializable