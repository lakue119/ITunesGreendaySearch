package com.lakue.itunesgreendaysearch.model

data class ResponseSearch(
    val resultCount: Int,
    val results: List<Track>
)