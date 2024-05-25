package com.example.data.network

import com.example.domain.data.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NewsApi (
    private val httpClient: HttpClient
) {
    suspend fun getNews(category: String): NewsResponse =
        httpClient.get("https://newsapi.org/v2/top-headlines?country=in&category=$category&apiKey=1c533168072d49f096cbd1a0fb562cd9")

}