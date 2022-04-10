package com.n.githubsample.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

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
}