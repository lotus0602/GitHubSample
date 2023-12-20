package com.n.githubsample.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.core.DataStoreManager
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.Repo
import com.n.githubsample.domain.usecase.GetRepoUseCase
import com.n.githubsample.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val dataStore: DataStoreManager,
    private val getUserUseCase: GetUserUseCase,
    private val getRepoUseCase: GetRepoUseCase
) : ViewModel() {
    val repoList: MutableLiveData<List<Repo>> = MutableLiveData<List<Repo>>()

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

    fun checkLogin() {

        flow<Boolean> {
            hasAccessToken.first()
        }
    }

    fun getUser(accessToken: String) {
        viewModelScope.launch {
            getUserUseCase(accessToken).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d(
                            "TAG",
                            "getUser, ${result.data.id}, ${result.data.name}, ${result.data.email}"
                        )
                    }
                    is Result.Fail -> Unit
                }
            }
        }
    }

    fun getRepo() {
        viewModelScope.launch {
            getRepoUseCase().collect { res ->
                when (res) {
                    is Result.Success -> repoList.value = res.data
                    is Result.Fail -> Unit
                }
            }
        }
    }
}

sealed interface SplashState {
    object Loading : SplashState
    object Complete : SplashState
}