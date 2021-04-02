package com.lakue.itunesgreendaysearch.api

import com.lakue.itunesgreendaysearch.model.ResponseSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/search")
    suspend fun getiTunesSearch(
            @Query("term") term: String,
            @Query("entity") entity: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
    ): Response<ResponseSearch>
}