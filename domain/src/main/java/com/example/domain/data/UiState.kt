package com.example.domain.data

sealed class UiState {
    data object Loading: UiState()
    data object Error : UiState()
    data object Success: UiState()
}