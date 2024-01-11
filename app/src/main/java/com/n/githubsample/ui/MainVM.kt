package com.n.githubsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.core.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val _splashState = MutableStateFlow<SplashState>(SplashState.Loading)

    private val _hasAccessToken = MutableSharedFlow<Boolean>()
    val hasAccessToken = _hasAccessToken.asSharedFlow()

    fun keepSplash(): Boolean = _splashState.value !is SplashState.Complete

    fun initialize() {
        viewModelScope.launch {
            val accessToken = dataStore.getAccessToken().first()

            _hasAccessToken.emit(accessToken.isNotEmpty())

            _splashState.update { SplashState.Complete }
        }
    }
}

sealed interface SplashState {
    object Loading : SplashState
    object Complete : SplashState
}