package com.shijas.shoppingapp.data.source.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeDto(
    @Json(name = "homeData")
    val homeData: List<HomeData>?,
    @Json(name = "status")
    val status: Boolean?
)