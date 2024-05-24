package com.example.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NewsApi (
    private val httpClient: HttpClient
) {
    suspend fun getNews(): Boolean =
        httpClient.get("https://newsapi.org/v2/top-headlines?country=in&apiKey=c5d9487bb44047d380c70d1810160353")

}