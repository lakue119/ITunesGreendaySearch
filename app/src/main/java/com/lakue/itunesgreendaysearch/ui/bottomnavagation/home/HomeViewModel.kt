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
import com.lakue.itunesgreendaysearch.utils.LogUtil
import com.lakue.itunesgreendaysearch.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Integer.max
import java.lang.Integer.min
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val networkHelper: NetworkHelper,
        var db: FavoriteTrackRepository,
        private val iTunesRepository: ITunesRepository
) : BaseViewModel() {

    val LIMIT_COUNT = 20

    private val _liveMusic = MutableLiveData<ArrayList<Track>>()
    val liveMusic: LiveData<ArrayList<Track>> = _liveMusic
    val arrMusic = ArrayList<Track>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var rvloading = false

    private val _musickDetailEvent = MutableLiveData<Event<Pair<ArrayList<Track>, Int>>>()
    val musickDetailEvent: LiveData<Event<Pair<ArrayList<Track>, Int>>> = _musickDetailEvent

    private lateinit var favoriteTrackIds: List<Int>
    private lateinit var favoriteTracks: List<Track>

    val adapter = HomeAdapter(this)

    var liveEmpty = MutableLiveData<Boolean>(false)

    init {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
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

            fun checkEmpty() {
                liveEmpty.postValue(adapter.itemCount == 0)
            }
        })
    }

    val favoriteTrack: Function2<Track, Boolean, Unit> = this::onFavorite

    //체크박스 Select Check
    fun onFavorite(music: Track, isFavorite: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            music.favorite = true
            if (isFavorite) {
                db.insertTrack(music)
            } else {
                db.insertTrack(music)
            }
        }
    }


    val rvBottomCatch: Function1<Int, Unit> = this::onBottomCatch

    //RecyclerView Bottom Catch
    fun onBottomCatch(aa: Int) {
        if(!rvloading && aa >= adapter.itemCount - 2){
            rvloading = true
            fetchiTunesMusic()
        }
    }

    fun fetchFavoriteTrack() {
        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            favoriteTrackIds = db.fetchTrackIds()
        }
    }

    fun fetchiTunesMusic() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                //네트워크 연결
                iTunesRepository.getiTunesSearch(
                        "greenday",
                        "song",
                        LIMIT_COUNT,
                        adapter.itemCount).let { reponseSearch ->
                    if (reponseSearch.isSuccessful) {
                        //API Success
                        val data = reponseSearch.body()!!.results
                        for (track in data) {
                            if (favoriteTrackIds.contains(track.trackId)) {
                                track.favorite = true
                            }
                        }
                        arrMusic.addAll(data)
                        _liveMusic.postValue(arrMusic)
                        adapter.addItems(ArrayList(data))
                        _loading.value = false
                        rvloading = false
                    } else {
                        //Api Fail
                    }
                }
            } else {
                //네트워크 연결 실패

            }
        }
    }

    fun fetchFavoriteAll() {
        _loading.value = false
        CoroutineScope(Dispatchers.IO).launch {
            favoriteTracks = db.fetchTracks()

            arrMusic.addAll(favoriteTracks)
            _liveMusic.postValue(arrMusic)
            adapter.addItems(ArrayList(favoriteTracks))
        }
    }

    fun onMusicDetail(pos: Int) {
        var position = pos - max(0,pos-20)
        var limitMusicList = ArrayList(arrMusic.subList(max(0,pos-20),min(pos+20,arrMusic.size)))
        _musickDetailEvent.value = Event(Pair(limitMusicList, pos))
    }
}