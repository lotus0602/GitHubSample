package com.n.githubsample.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.usecase.GetAccessTokenUseCase
import com.n.githubsample.domain.usecase.GetDeviceCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class GitHubAuthVM @Inject constructor(
    private val getDeviceCodeUseCase: GetDeviceCodeUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    val userCode = MutableLiveData("")
    val deviceCode = MutableLiveData("")
    val verificationUri = MutableLiveData("")

    suspend fun getDeviceCode(clientID: String, scope: String) =
        getDeviceCodeUseCase.invoke(clientID, scope)
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        userCode.postValue(result.data.userCode)
                        deviceCode.postValue(result.data.deviceCode)
                        verificationUri.postValue(result.data.verificationUri)
                    }
                    is Result.Error -> Unit
                    Result.Loading -> Unit
                }
            }

    fun getAccessToken(
        clientID: String,
        deviceCode: String,
        grantType: String = "urn:ietf:params:oauth:grant-type:device_code"
    ): Flow<Result<String>> = getAccessTokenUseCase.invoke(clientID, deviceCode, grantType)
}