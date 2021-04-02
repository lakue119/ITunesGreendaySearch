package com.lakue.itunesgreendaysearch.repository

import com.lakue.itunesgreendaysearch.api.ApiHelper
import javax.inject.Inject

class ITunesRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getiTunesSearch(term:String, entity: String, limit: Int, offset: Int) =  apiHelper.getiTunesSearch(term, entity, limit, offset)
}