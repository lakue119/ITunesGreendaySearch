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

    val TAG = "HomeViewModel"

    val LIMIT_COUNT = 30

    private val _liveMusic = MutableLiveData<ArrayList<Track>>()
    val liveMusic: LiveData<ArrayList<Track>> = _liveMusic
    val arrMusic = ArrayList<Track>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var rvloading = false

    private val _musickDetailEvent = MutableLiveData<Event<Triple<ArrayList<Track>, Int, Int>>>()
    val musickDetailEvent: LiveData<Event<Triple<ArrayList<Track>, Int, Int>>> = _musickDetailEvent

    private lateinit var favoriteTrackIds: List<Int>
    private lateinit var favoriteTracks: List<Track>

    val rvBottomCatch: Function1<Int, Unit> = this::onBottomCatch
    val favoriteTrack: Function2<Track, Boolean, Unit> = this::onFavorite

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
    }

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

    fun favoriteTrackCheck(trackPositions: ArrayList<Int>){
        _loading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            favoriteTrackIds = db.fetchTrackIds()

            for(pos in trackPositions){
                var trackId = arrMusic[pos].trackId
                if (favoriteTrackIds.contains(trackId)) {
                    LogUtil.i(TAG, "$pos :  isFavorite : true")
                    adapter.favoriteTrackChanged(pos, true)
                } else {
                    LogUtil.i(TAG, "$pos :  isFavorite : false")
                    adapter.favoriteTrackChanged(pos, false)
                }
            }
            _loading.postValue(false)
            rvloading = false
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

    fun onMusicDetail(homePos: Int) {
        //detail 0번째 position
        LogUtil.d(TAG, "vm homePos : $homePos")
        var position = homePos - max(0,homePos-30)
        var arrTracks = liveMusic.value!!
        var limitMusicList = ArrayList(arrTracks.subList(max(0,homePos-30),min(homePos+30,arrTracks.size)))
        _musickDetailEvent.value = Event(Triple(limitMusicList, position, homePos))
    }
}