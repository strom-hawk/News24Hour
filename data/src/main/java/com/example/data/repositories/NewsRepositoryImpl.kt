package com.example.data.repositories

import com.example.domain.data.NewsResponse
import com.example.data.network.NewsApi
import com.example.domain.repositories.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {
    override suspend fun getNews(): NewsResponse {
        return api.getNews()
    }

}