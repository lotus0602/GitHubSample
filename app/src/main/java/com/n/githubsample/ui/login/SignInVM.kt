package com.n.githubsample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.Config
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.DeviceCode
import com.n.githubsample.domain.usecase.GetAccessTokenUseCase
import com.n.githubsample.domain.usecase.GetDeviceCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInVM @Inject constructor(
    private val getDeviceCodeUseCase: GetDeviceCodeUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    // @see: https://docs.github.com/en/developers/apps/building-oauth-apps/scopes-for-oauth-apps#available-scopes
    private val SCOPE_LIST = arrayOf("repo", "user")
    private val GRANT_TYPE = "urn:ietf:params:oauth:grant-type:device_code"

    private val _deviceCodeUiState = MutableStateFlow<DeviceCodeUiState>(DeviceCodeUiState.Loading)
    val deviceCodeUiState = _deviceCodeUiState.asStateFlow()

    private val _accessTokenUiState = MutableStateFlow<AccessTokenUiState>(AccessTokenUiState.None)
    val accessTokenUiState = _accessTokenUiState.asStateFlow()

    private val _expireState = MutableStateFlow<ExpireState>(ExpireState.Idle)
    val expireState = _expireState.asStateFlow()

    private fun startExpiresTimer(time: Int) {
        viewModelScope.launch {
            _expireState.update { ExpireState.UnExpired(time, 0) }
            while (_expireState.value is ExpireState.UnExpired) {
                delay(1_000)

                val oldState = (_expireState.value as ExpireState.UnExpired)
                val newState = oldState.copy(passedTime = oldState.passedTime + 1)

                _expireState.update { newState }

                if (newState.isExceed()) {
                    break
                }
            }
            _expireState.update { ExpireState.Expired }
        }
    }

    fun getDeviceCode(
        clientID: String = Config.Key.GITHUB_CLIENT_ID,
        scope: String = SCOPE_LIST.joinToString(" ")
    ) {
        viewModelScope.launch {
            _accessTokenUiState.update { AccessTokenUiState.None }

            if (_expireState.value.isUnExpired()) {
                return@launch
            }

            getDeviceCodeUseCase(clientID, scope)
                .onStart { _deviceCodeUiState.update { DeviceCodeUiState.Loading } }
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            startExpiresTimer(result.data.expiresIn)
                            _deviceCodeUiState.update { DeviceCodeUiState.toSuccess(result.data) }
                        }

                        is Result.Fail -> {
                            _deviceCodeUiState.update {
                                DeviceCodeUiState.Error(
                                    errorType = result.errorType(),
                                    errorMessage = result.errorMessage()
                                )
                            }
                        }
                    }
                }
        }
    }

    fun getAccessToken(
        clientID: String = Config.Key.GITHUB_CLIENT_ID,
        grantType: String = GRANT_TYPE
    ) {
        viewModelScope.launch {
            if (deviceCodeUiState.value is DeviceCodeUiState.Success) {
                val deviceCode = (deviceCodeUiState.value as DeviceCodeUiState.Success).deviceCode

                getAccessTokenUseCase(clientID, deviceCode, grantType)
                    .onStart { _accessTokenUiState.update { AccessTokenUiState.Loading } }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _accessTokenUiState.update { AccessTokenUiState.Success(result.data) }
                            }

                            is Result.Fail -> {
                                _accessTokenUiState.update {
                                    AccessTokenUiState.Error(
                                        errorType = result.errorType(),
                                        errorMessage = result.errorMessage()
                                    )
                                }
                            }
                        }
                    }
            } else {
                _accessTokenUiState.update {
                    AccessTokenUiState.Error(
                        errorType = "App",
                        errorMessage = "Fail to Complete previous step"
                    )
                }
            }
        }
    }
}

sealed interface DeviceCodeUiState {
    object Loading : DeviceCodeUiState

    data class Success(
        val userCode: String,
        val deviceCode: String,
        val verificationUri: String
    ) : DeviceCodeUiState

    data class Error(
        val errorType: String,
        val errorMessage: String
    ) : DeviceCodeUiState

    companion object {
        fun toSuccess(data: DeviceCode): Success =
            Success(
                userCode = data.userCode,
                deviceCode = data.deviceCode,
                verificationUri = data.verificationUri
            )
    }
}

sealed interface AccessTokenUiState {
    object None : AccessTokenUiState
    object Loading : AccessTokenUiState

    data class Success(
        val accessToken: String
    ) : AccessTokenUiState

    data class Error(
        val errorType: String,
        val errorMessage: String
    ) : AccessTokenUiState
}

sealed interface ExpireState {
    object Idle : ExpireState
    object Expired : ExpireState
    data class UnExpired(val expiredTime: Int, val passedTime: Int) : ExpireState {
        fun isExceed(): Boolean = expiredTime <= passedTime
        fun toTime(): String =
            "${(expiredTime - passedTime) / 60}:${(expiredTime - passedTime) % 60}"
    }

    fun isUnExpired(): Boolean = this is UnExpired
}