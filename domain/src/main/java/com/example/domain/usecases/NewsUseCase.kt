package com.example.domain.usecases

import com.example.domain.data.NewsResponse
import com.example.domain.repositories.NewsRepository

class NewsUseCase(
    private val repo: NewsRepository
) {
    suspend fun getNews(): NewsResponse {
        return repo.getNews()
    }
}