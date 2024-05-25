package com.example.domain.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<Articles>? = null
)

@Serializable
data class Articles(
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val publishedAt: String? = null,

    @SerialName("urlToImage") val imageUrl: String? = null
)

@Serializable
data class Source(
    val id: String? = null,
    val name: String? = null,
)


