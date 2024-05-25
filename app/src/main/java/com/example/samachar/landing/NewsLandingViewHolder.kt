package com.example.samachar.landing

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.data.NewsResponse
import com.example.domain.data.UiState
import com.example.samachar.utils.customviews.CustomLoader

@Composable
fun NewsLandingViewHolder(
    uiState: State<UiState>,
    newsResponse: State<NewsResponse>
) {
    val news = newsResponse.value
    when (uiState.value) {
        UiState.Loading -> { CustomLoader() }
        UiState.Error -> { println("_________error: ") }
        else -> {
            NewsListView(news)
        }
    }
}

@Composable
fun NewsListView(
    news: NewsResponse
) {
    LazyColumn {
        news.articles?.forEach { article ->
            item {
                Text(text = article.title ?: "Title not available")
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}