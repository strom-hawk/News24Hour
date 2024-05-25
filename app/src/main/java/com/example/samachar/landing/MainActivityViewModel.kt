package com.example.samachar.landing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data.NewsResponse
import com.example.domain.data.Response
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
    private val _newsResponse = MutableStateFlow<Response<NewsResponse>?>(value = null)
    val newsResponse = _newsResponse.asStateFlow()

    fun getNews(
        category: String = "GENERAL"
    ) {
        _newsResponse.value = Response.Loading()
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { coroutineContext, throwable ->

            _newsResponse.value =
                Response.Error(throwable.localizedMessage ?: "Something went wrong")
        }) {
            _newsResponse.value = Response.Success(useCase.getNews(category))
        }
    }
}