package com.n.githubsample.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.Config
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.usecase.GetAccessTokenUseCase
import com.n.githubsample.domain.usecase.GetDeviceCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubAuthVM @Inject constructor(
    private val getDeviceCodeUseCase: GetDeviceCodeUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private lateinit var receivedDeviceCode: String

    val userCode = MutableLiveData("")
    val verificationUri = MutableLiveData("")

    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String> = _accessToken

    fun getDeviceCode(
        clientID: String = Config.Key.GITHUB_CLIENT_ID,
        scope: String = SCOPE_LIST.joinToString(" ")
    ) {
        viewModelScope.launch {
            getDeviceCodeUseCase(clientID, scope)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            receivedDeviceCode = result.data.deviceCode
                            userCode.postValue(result.data.userCode)
                            verificationUri.postValue(result.data.verificationUri)
                        }

                        is Result.Error -> Unit
                    }
                }
        }
    }

    fun getAccessToken(
        clientID: String = Config.Key.GITHUB_CLIENT_ID,
        deviceCode: String = receivedDeviceCode,
        grantType: String = GRANT_TYPE
    ) {
        viewModelScope.launch {
            getAccessTokenUseCase(clientID, deviceCode, grantType)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _accessToken.postValue(result.data)
                        }

                        is Result.Error -> Unit
                    }
                }
        }
    }

    companion object {

        private const val GRANT_TYPE = "urn:ietf:params:oauth:grant-type:device_code"

        // @see: https://docs.github.com/en/developers/apps/building-oauth-apps/scopes-for-oauth-apps#available-scopes
        private val SCOPE_LIST = arrayOf("repo", "user")
    }
}