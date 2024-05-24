package com.example.domain.repositories

interface NewsRepository {
    suspend fun getNews(): Boolean
}