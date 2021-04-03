package com.lakue.itunesgreendaysearch.ui.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lakue.itunesgreendaysearch.base.BaseViewModel
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.utils.Event
import javax.inject.Inject

class MusicViewModel  @Inject constructor(
) : BaseViewModel() {

    private val _liveTracks = MutableLiveData<ArrayList<Track>>()
    val liveTracks: LiveData<ArrayList<Track>> = _liveTracks

    private val _liveNowTrackPosition = MutableLiveData<Int>()
    val liveNowTrackPosition: LiveData<Int> = _liveNowTrackPosition

    private val _musickDetailEvent = MutableLiveData<Event<Track>>()
    val musickDetailEvent: LiveData<Event<Track>> = _musickDetailEvent

    var tracksSize = 0

    fun setTracks(tracks: ArrayList<Track>, pos: Int){
        tracksSize = tracks.size
        _liveTracks.value = tracks
        _liveNowTrackPosition.value = pos
        _musickDetailEvent.value = Event(liveTracks.value!![liveNowTrackPosition.value!!])
    }

    fun onNextMusic(){
        var position = _liveNowTrackPosition.value!!
        if(position >= tracksSize-1){
            position = 0
        } else {
            position++
        }
        _liveNowTrackPosition.value = position
        _musickDetailEvent.value = Event(liveTracks.value!![position])
    }

    fun onBeforeMusic(){
        var position = _liveNowTrackPosition.value!!
        if(position <= 0){
            position = tracksSize-1
        } else {
            position--
        }
        _liveNowTrackPosition.value = position
        _musickDetailEvent.value = Event(liveTracks.value!![position])
    }

}