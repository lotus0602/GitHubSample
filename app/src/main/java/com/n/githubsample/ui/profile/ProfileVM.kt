package com.n.githubsample.ui.profile

import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val getUserUseCase: GetUserUseCase,
    private val getMyRepoUseCase: GetMyRepoUseCase
) : ViewModel() {
    val user = MutableLiveData<User>()
    val repoList = MutableLiveData<List<MyPopularRepo>>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchData() {
        viewModelScope.launch {
            val token = dataStoreManager.getAccessToken().first()
            if (token.isEmpty()) {
                return@launch
            }
            getUserUseCase(token)
                .transformLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            user.postValue(result.data)
                            emit(result.data.login)
                        }

                        is Result.Fail -> error(result.errorMessage())
                    }
                }.flatMapLatest { userName -> getMyRepoUseCase(userName) }
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> repoList.postValue(result.data)
                        is Result.Fail -> error(result.errorMessage())
                    }
                }
        }
    }
}