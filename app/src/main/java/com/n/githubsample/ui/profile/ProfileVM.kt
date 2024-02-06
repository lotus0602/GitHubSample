package com.n.githubsample.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.core.DataStoreManager
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.MyPopularRepo
import com.n.githubsample.domain.model.User
import com.n.githubsample.domain.usecase.GetMyRepoUseCase
import com.n.githubsample.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getUserUseCase: GetUserUseCase,
    private val getMyRepoUseCase: GetMyRepoUseCase
) : ViewModel() {
    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState = _profileUiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchData() {
        viewModelScope.launch {
            val token = dataStoreManager.getAccessToken().first()
            if (token.isEmpty()) {
                _profileUiState.emit(ProfileUiState.Error(Exception("token is empty")))
                return@launch
            }

            getUserUseCase(token)
                .transformLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            emit(result.data)
                        }

                        is Result.Fail -> error(result.errorMessage())
                    }
                }.transformLatest { user ->
                    getMyRepoUseCase(user.login).collect { result ->
                        when (result) {
                            is Result.Success -> {
                                emit(ProfileUiState.Success(user, result.data))
                            }

                            is Result.Fail -> error(result.errorMessage())
                        }
                    }
                }.collectLatest { result ->
                    _profileUiState.emit(result)
                }
        }
    }
}

sealed interface ProfileUiState {
    object Loading : ProfileUiState

    data class Success(
        val user: User,
        val repositories: List<MyPopularRepo> = emptyList()
    ) : ProfileUiState

    data class Error(
        val error: Exception
    ) : ProfileUiState
}