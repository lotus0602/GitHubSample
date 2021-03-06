package com.n.githubsample.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.Repo
import com.n.githubsample.domain.usecase.GetRepoUseCase
import com.n.githubsample.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getRepoUseCase: GetRepoUseCase
) : ViewModel() {
    val repoList: MutableLiveData<List<Repo>> = MutableLiveData<List<Repo>>()

    fun getUser(accessToken: String) {
        viewModelScope.launch {
            getUserUseCase(accessToken).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("TAG", "getUser, ${result.data.id}, ${result.data.name}, ${result.data.email}")
                    }
                    is Result.Error -> Unit
                    Result.Loading -> Unit
                }
            }
        }
    }

    fun getRepo() {
        viewModelScope.launch {
            getRepoUseCase().collect { res ->
                when (res) {
                    is Result.Success -> repoList.value = res.data
                    is Result.Error -> Unit
                    Result.Loading -> Unit
                }
            }
        }
    }
}