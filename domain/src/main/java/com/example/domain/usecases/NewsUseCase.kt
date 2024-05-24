package com.example.domain.usecases

import com.example.domain.repositories.NewsRepository

class NewsUseCase(
    private val repo: NewsRepository
) {
    suspend fun getNews(): Boolean {
        return repo.getNews()
    }
}