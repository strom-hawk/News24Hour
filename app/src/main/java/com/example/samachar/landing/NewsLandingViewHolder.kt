package com.example.samachar.landing

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.data.Articles
import com.example.domain.data.NewsResponse
import com.example.domain.data.Response
import com.example.domain.data.UiState
import com.example.samachar.R
import com.example.samachar.utils.ColorSystem
import com.example.samachar.utils.customviews.ErrorState
import com.example.samachar.utils.customviews.NewsCard
import com.example.samachar.utils.customviews.ShimmerBrush
import kotlinx.coroutines.launch

@Composable
fun NewsLandingViewHolder(
    newsResponse: State<Response<NewsResponse>?>,
    onNewsCategoryClick: (String) -> Unit
) {
    var newsList: List<Articles> = listOf()
    var errorMessage = ""
    val uiStateController = remember { mutableStateOf<UiState>(UiState.Loading) }

    when (newsResponse.value) {
        is Response.Loading -> {
            uiStateController.value = UiState.Loading
        }

        is Response.Error -> {
            newsResponse.value?.errorMessage?.let { errorMessage = it }
            uiStateController.value = UiState.Error
        }

        is Response.Success -> {
            newsResponse.value?.data?.let {
                it.articles?.let { articles ->
                    newsList = articles
                }

            }
            uiStateController.value = UiState.Success
        }

        null -> {}
    }

    NewsView(
        news = newsList,
        errorMessage = errorMessage,
        uiStateController = uiStateController.value,
        onNewsCategoryClick = onNewsCategoryClick
    )
}

@Composable
fun NewsView(
    news: List<Articles>,
    errorMessage: String,
    uiStateController: UiState,
    onNewsCategoryClick: (String) -> Unit
) {
    val newsCardFocus = FocusRequester()
    val categoryFocus = FocusRequester()

    LaunchedEffect(Unit) {
        categoryFocus.requestFocus()
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorSystem.grey_900)
    ) {
        RedDivider()
        CategoryList(
            focusRequester = newsCardFocus,
            categoryFocus = categoryFocus,
            onNewsCategoryClick = onNewsCategoryClick
        )
        NewsList(
            news = news,
            errorMessage = errorMessage,
            uiStateController = uiStateController,
            newsCardFocus = newsCardFocus,
            categoryFocus = categoryFocus
        )
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
fun CategoryList(
    focusRequester: FocusRequester,
    categoryFocus: FocusRequester,
    onNewsCategoryClick: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val categoryList =
        listOf("General", "Business", "Entertainment", "Health", "Science", "Sports", "Technology")
    val selectedIndex = remember { mutableStateOf(0) }
    val selectedTab = remember { mutableStateOf(categoryList[selectedIndex.value]) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxSize(0.2F)
            .background(ColorSystem.grey_800),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painterResource(R.drawable.samachar_logo),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            categoryList.forEachIndexed { index, category ->
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (selectedTab.value == category) ColorSystem.grey_700 else Color.Transparent)
                            .padding(vertical = 16.dp, horizontal = 10.dp)
                            .focusRequester(categoryFocus)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                selectedIndex.value = index
                                selectedTab.value = category
                                onNewsCategoryClick(category)
                            }
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                                    when (keyEvent.nativeKeyEvent.keyCode) {
                                        KeyEvent.KEYCODE_DPAD_CENTER -> {
                                            println("-----------long press")
                                            true
                                        }

                                        else -> {
                                            false
                                        }
                                    }
                                }

                                if (keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
                                    when (keyEvent.nativeKeyEvent.keyCode) {
                                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                                            if (selectedIndex.value + 1 < categoryList.size) {
                                                selectedIndex.value += 1
                                                selectedTab.value =
                                                    categoryList[selectedIndex.value]
                                                onNewsCategoryClick(selectedTab.value)
                                            }
                                            true
                                        }

                                        KeyEvent.KEYCODE_DPAD_UP -> {
                                            if (selectedIndex.value - 1 >= 0) {
                                                selectedIndex.value -= 1
                                                selectedTab.value =
                                                    categoryList[selectedIndex.value]
                                                onNewsCategoryClick(selectedTab.value)
                                            }
                                            true
                                        }

                                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                                            focusRequester.requestFocus()
                                            true
                                        }

                                        else -> {
                                            false
                                        }

                                    }
                                } else {
                                    true
                                }
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
    news: List<Articles>,
    errorMessage: String,
    uiStateController: UiState,
    newsCardFocus: FocusRequester,
    categoryFocus: FocusRequester
) {
    val itemSize: Dp = ((LocalConfiguration.current.screenWidthDp.dp)) / 6

    when (uiStateController) {
        UiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ShimmerBrush(targetValue = 1300f, showShimmer = true))
            ) {}
        }

        UiState.Error -> {
            ErrorState(errorMessage)
        }

        else -> {

            val selectedCardIndex = remember { mutableStateOf(0) }
            val lazyGridState = rememberLazyGridState()
            val coroutineScope = rememberCoroutineScope()

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                columns = GridCells.Adaptive(minSize = itemSize),
                state = lazyGridState,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                news.forEachIndexed { index, article ->
                    item {
                        NewsCard(
                            selectedCardIndex.value,
                            index,
                            article,
                            newsCardFocus
                        ) { keyEvent, selectedIndex ->
                            if(keyEvent == KeyEvent.KEYCODE_DPAD_LEFT && (selectedIndex+1) % 4 == 0) {
                                categoryFocus.requestFocus()
                            } else {
                                if (keyEvent == KeyEvent.KEYCODE_DPAD_DOWN && selectedIndex < news.size) {
                                    selectedCardIndex.value = selectedIndex
                                } else if (keyEvent == KeyEvent.KEYCODE_DPAD_UP && selectedIndex >= 0) {
                                    selectedCardIndex.value = selectedIndex
                                } else if (keyEvent == KeyEvent.KEYCODE_DPAD_RIGHT && selectedIndex <= news.size) {
                                    selectedCardIndex.value = selectedIndex
                                } else if (keyEvent == KeyEvent.KEYCODE_DPAD_LEFT && selectedIndex >= 0) {
                                    selectedCardIndex.value = selectedIndex
                                }

                                coroutineScope.launch {
                                    lazyGridState.animateScrollToItem(selectedCardIndex.value)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}