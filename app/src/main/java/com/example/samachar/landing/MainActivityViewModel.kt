package com.example.samachar.landing

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data.NewsResponse
import com.example.domain.data.UiState
import com.example.domain.usecases.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: NewsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState

    private val _newsResponse = MutableStateFlow(value = NewsResponse())
    val newsResponse = _newsResponse.asStateFlow()

    fun getNews(
        category: String = "GENERAL"
    ) {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { coroutineContext, throwable ->
            uiState.value = UiState.Error
        }) {
            _newsResponse.value = useCase.getNews(category)
            uiState.value = UiState.Success
        }
    }
}