package com.n.githubsample.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.User
import com.n.githubsample.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    val user = MutableLiveData<User>()

    suspend fun getUser(accessToken: String) {
        getUserUseCase.invoke(accessToken).collect { result ->
            when (result) {
                is Result.Success -> {
                    user.postValue(result.data)
                }
                is Result.Error -> Unit
                Result.Loading -> Unit
            }
        }
    }
}