package com.lakue.itunesgreendaysearch.api

import com.lakue.itunesgreendaysearch.model.ResponseSearch
import retrofit2.Response

interface ApiHelper {
    suspend fun getiTunesSearch(term: String, entity: String): Response<ResponseSearch>
}