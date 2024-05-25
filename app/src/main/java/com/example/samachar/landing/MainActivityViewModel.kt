package com.example.samachar.landing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCase: NewsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getNews() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { coroutineContext, throwable ->
            println("____________${throwable}")
        }) {
            val response = useCase.getNews()
            println("_____ status: ${response.status}")
            println("_____ total news: ${response.totalResults}")
        }
    }
}