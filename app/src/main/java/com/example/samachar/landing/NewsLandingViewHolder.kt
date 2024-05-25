package com.example.samachar.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.data.NewsResponse
import com.example.domain.data.UiState
import com.example.samachar.utils.ColorSystem
import com.example.samachar.utils.customviews.NewsCard
import com.example.samachar.utils.customviews.ShimmerBrush

@Composable
fun NewsLandingViewHolder(
    uiState: State<UiState>,
    newsResponse: State<NewsResponse>,
    onNewsCategoryClick: (String) -> Unit
) {
    val news = newsResponse.value
    val showShimmer = remember { mutableStateOf(true) }

    when (uiState.value) {
        UiState.Loading -> {
            showShimmer.value = true
        }

        UiState.Error -> {
            println("_________error: ")
            showShimmer.value = true
        }

        else -> {
            showShimmer.value = false
        }
    }

    NewsView(
        news = news,
        showShimmer = showShimmer.value,
        onNewsCategoryClick = onNewsCategoryClick
    )
}

@Composable
fun NewsView(
    news: NewsResponse,
    showShimmer: Boolean,
    onNewsCategoryClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorSystem.grey_900)
    ) {
        RedDivider()
        CategoryList(onNewsCategoryClick = onNewsCategoryClick)
        NewsList(news = news, showShimmer = showShimmer)
    }
}

@Composable
fun RedDivider() {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxSize(0.01F),
        color = ColorSystem.red_900
    )
}

@Composable
fun CategoryList(onNewsCategoryClick: (String) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val selectedTab = remember { mutableStateOf("General") }
    val categoryList =
        listOf("General", "Business", "Entertainment", "Health", "Science", "Sports", "Technology")

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxSize(0.2F)
            .background(ColorSystem.grey_800),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn {
            categoryList.forEach { category ->
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (selectedTab.value == category) ColorSystem.grey_700 else Color.Transparent)
                            .padding(vertical = 16.dp, horizontal = 10.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                selectedTab.value = category
                                onNewsCategoryClick(category)
                            },
                        text = category.uppercase(),
                        color = ColorSystem.white,
                    )
                }
            }
        }

    }
}

@Composable
fun NewsList(
    news: NewsResponse,
    showShimmer: Boolean,
) {
    val itemSize: Dp = ((LocalConfiguration.current.screenWidthDp.dp)) / 5

    if (showShimmer) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ShimmerBrush(targetValue = 1300f, showShimmer = showShimmer))
        ) {}
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
            columns = GridCells.Adaptive(minSize = itemSize),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            news.articles?.forEach { article ->
                item {
                    NewsCard(article)
                }
            }
        }
    }
}