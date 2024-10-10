package com.example.addassignment

import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaApiService {
    @GET("items")
    suspend fun getQiitaArticles(@Query("query") query: String): List<QiitaArticle>
}