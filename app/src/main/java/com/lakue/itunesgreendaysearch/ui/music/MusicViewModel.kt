package com.lakue.itunesgreendaysearch.ui.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lakue.itunesgreendaysearch.base.BaseViewModel
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.repository.FavoriteTrackRepository
import com.lakue.itunesgreendaysearch.utils.Event
import com.lakue.itunesgreendaysearch.utils.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel  @Inject constructor(
    var db: FavoriteTrackRepository
) : BaseViewModel() {

    private val _liveTracks = MutableLiveData<ArrayList<Track>>()
    val liveTracks: LiveData<ArrayList<Track>> = _liveTracks

    private val _liveNowTrackPosition = MutableLiveData<Int>()
    val liveNowTrackPosition: LiveData<Int> = _liveNowTrackPosition

    private val _musickDetailEvent = MutableLiveData<Event<Track>>()
    val musickDetailEvent: LiveData<Event<Track>> = _musickDetailEvent

    val favoriteTrack: Function2<Track, Boolean, Unit> = this::onFavorite

    var tracksSize = 0
    var changeTrachPosition = ArrayList<Int>()

    var homeTrackPos = 0
    var midPos = 0

    fun setTracks(tracks: ArrayList<Track>, pos: Int, homeTrackPos: Int){
        this.midPos = pos
        this.homeTrackPos = homeTrackPos
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

    //체크박스 Select Check
    fun onFavorite(music: Track, isFavorite: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            music.favorite = true
            if (isFavorite) {
                db.insertTrack(music)
            } else {
                db.deleteTrack(music)
            }
        }

        var nowPosition = liveNowTrackPosition.value!!
        var homePosition = 0
        if(nowPosition <= midPos){
            homePosition = homeTrackPos - (midPos - nowPosition)
        } else {
            homePosition = homeTrackPos + (nowPosition - midPos)
        }
        if(!changeTrachPosition.contains(homePosition)){
            changeTrachPosition.add(homePosition)
        }
    }

}