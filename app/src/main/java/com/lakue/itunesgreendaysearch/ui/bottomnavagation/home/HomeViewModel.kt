package com.lakue.itunesgreendaysearch.ui.bottomnavagation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.lakue.itunesgreendaysearch.base.BaseViewModel
import com.lakue.itunesgreendaysearch.model.Track
import com.lakue.itunesgreendaysearch.repository.FavoriteTrackRepository
import com.lakue.itunesgreendaysearch.repository.ITunesRepository
import com.lakue.itunesgreendaysearch.utils.Event
import com.lakue.itunesgreendaysearch.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    var db: FavoriteTrackRepository,
    private val iTunesRepository: ITunesRepository
) : BaseViewModel() {

    private val _liveMusic = MutableLiveData<ArrayList<Track>>()
    val liveMusic: LiveData<ArrayList<Track>> = _liveMusic
    val arrMusic = ArrayList<Track>()

    val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    private val _musickDetailEvent = MutableLiveData<Event<Track>>()
    val musickDetailEvent: LiveData<Event<Track>> = _musickDetailEvent

    private lateinit var favoriteTrackIds: List<Int>
    private lateinit var favoriteTracks: List<Track>

    val adapter = HomeAdapter(this)

    var liveEmpty = MutableLiveData<Boolean>(false)

    init {
        adapter.registerAdapterDataObserver(object :RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                checkEmpty()
            }

            fun checkEmpty(){
                liveEmpty.postValue(adapter.itemCount == 0)
            }
        })
    }

    val favoriteTrack: Function2<Track, Boolean, Unit> = this::onFavorite
    //체크박스 Select Check
    fun onFavorite(music: Track, isFavorite: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            music.favorite = true
            if(isFavorite){
                db.insertTrack(music)
            } else {
                db.insertTrack(music)
            }
        }
    }

    fun fetchFavoriteTrack(){
        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            favoriteTrackIds = db.fetchTrackIds()
        }
    }

    fun fetchiTunesMusic(){
        viewModelScope.launch {
            if(networkHelper.isNetworkConnected()){
                //네트워크 연결
                iTunesRepository.getiTunesSearch(
                    "greenday",
                    "song").let {reponseSearch ->
                    if(reponseSearch.isSuccessful){
                        //API Success
                        var data = reponseSearch.body()!!.results
                        for(track in data){
                            if(favoriteTrackIds.contains(track.trackId)){
                                track.favorite = true
                            }
                        }
//                        data.map { favoriteTrackIds.contains(it.trackId)}
                        arrMusic.addAll(data)
                        _liveMusic.postValue(arrMusic)
                        adapter.addItems(ArrayList(data))
                        _loading.value = false
                    } else{
                        //Api Fail
                    }
                }
            } else {
                //네트워크 연결 실패

            }
        }
    }

    fun fetchFavoriteAll(){
        _loading.value = false
        CoroutineScope(Dispatchers.IO).launch {
            favoriteTracks = db.fetchTracks()

            arrMusic.addAll(favoriteTracks)
            _liveMusic.postValue(arrMusic)
            adapter.addItems(ArrayList(favoriteTracks))
        }
    }

    fun onMusicDetail(item: Track){
        _musickDetailEvent.value = Event(item)
    }
}