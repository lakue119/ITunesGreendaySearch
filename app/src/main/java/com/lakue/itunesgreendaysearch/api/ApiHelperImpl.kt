package com.lakue.itunesgreendaysearch.api

import com.lakue.itunesgreendaysearch.model.ResponseSearch
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
        private val apiService: ApiService) : ApiHelper {

    override suspend fun getiTunesSearch(term: String, entity: String, limit: Int, offset: Int): Response<ResponseSearch> =
        apiService.getiTunesSearch(term, entity, limit, offset)

}