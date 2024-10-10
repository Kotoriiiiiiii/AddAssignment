package com.example.addassignment

import com.squareup.moshi.Json

data class QiitaArticle(
    @Json(name = "title") val title: String
)
