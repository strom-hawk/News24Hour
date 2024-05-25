package com.example.domain.repositories

import com.example.domain.data.NewsResponse

interface NewsRepository {
    suspend fun getNews(): NewsResponse
}