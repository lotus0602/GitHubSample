package com.n.githubsample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.Repo
import com.n.githubsample.domain.usecase.GetRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val getRepoUseCase: GetRepoUseCase
) : ViewModel() {
    val repoList: MutableLiveData<List<Repo>> = MutableLiveData<List<Repo>>()

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