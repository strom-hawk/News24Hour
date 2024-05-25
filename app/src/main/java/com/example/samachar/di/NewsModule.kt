package com.example.samachar.di

import com.example.data.network.NewsApi
import com.example.data.repositories.NewsRepositoryImpl
import com.example.domain.repositories.NewsRepository
import com.example.domain.usecases.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.http.ContentType
import javax.inject.Singleton

/**
 * Created by Saurav Suman on 18/01/24.
 */
@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                serializer = KotlinxSerializer(json)
                acceptContentTypes = acceptContentTypes + ContentType.Any
            }
        }
    }

    @Singleton
    @Provides
    fun providesNewsApi(
        httpClient: HttpClient
    ): NewsApi {
        return NewsApi(httpClient = httpClient)
    }

    @Singleton
    @Provides
    fun providesNewsRepository(api: NewsApi): NewsRepository =
        NewsRepositoryImpl(api)

    @Singleton
    @Provides
    fun providesNewsUseCaseUseCase(repo: NewsRepository): NewsUseCase {
        return NewsUseCase(repo)
    }
}