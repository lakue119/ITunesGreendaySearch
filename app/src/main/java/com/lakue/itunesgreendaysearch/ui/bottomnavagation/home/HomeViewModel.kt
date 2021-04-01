package com.lakue.itunesgreendaysearch.ui.bottomnavagation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lakue.itunesgreendaysearch.base.BaseViewModel
import com.lakue.itunesgreendaysearch.model.ResponseSearch
import com.lakue.itunesgreendaysearch.model.Result
import com.lakue.itunesgreendaysearch.repository.ITunesRepository
import com.lakue.itunesgreendaysearch.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val iTunesRepository: ITunesRepository
) : BaseViewModel() {

    private val _liveMusic = MutableLiveData<ArrayList<Result>>()
    val liveMusic: LiveData<ArrayList<Result>> = _liveMusic
    val arrMusic = ArrayList<Result>()

    val adapter = HomeAdapter(this)

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
                        arrMusic.addAll(data)
                        _liveMusic.postValue(arrMusic)
                        adapter.addItems(ArrayList(data))
                    } else{
                        //Api Fail
                    }
                }
            } else {
                //네트워크 연결 실패

            }
        }
    }

//    fun getLiveTest(): ResponseSearch{
//        return liveMusic.value!!
//    }

}