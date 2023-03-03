package com.eati.pexels.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotosResponse(
    @Json(name = "total_results")
    val totalResults: Int,
    val page: Int,
    @Json(name = "per_page")
    val perPage: Int,
    val photos: List<PhotoResponse>
)

@JsonClass(generateAdapter = true)
data class PhotoResponse(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @Json(name = "photographer_url")
    val photographerUrl: String,
    @Json(name = "photographer_id")
    val photographerId: Int,
    @Json(name = "avg_color")
    val avgColor: String,
    val src: Source,
    val liked: Boolean,
    val alt: String
)

@JsonClass(generateAdapter = true)
data class Source(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
