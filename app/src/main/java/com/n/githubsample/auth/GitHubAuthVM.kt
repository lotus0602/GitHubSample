package com.n.githubsample.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.Config
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.usecase.GetAccessTokenUseCase
import com.n.githubsample.domain.usecase.GetDeviceCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubAuthVM @Inject constructor(
    private val getDeviceCodeUseCase: GetDeviceCodeUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    private val _deviceCodeState = MutableStateFlow<DeviceCodeState>(DeviceCodeState.None)
    val deviceCodeState = _deviceCodeState.asStateFlow()

    private val _accessTokenState = MutableStateFlow<AccessTokenState>(AccessTokenState.None)
    val accessTokenState = _accessTokenState.asStateFlow()

    val userCode: StateFlow<String> =
        deviceCodeState.map { state ->
            if (state is DeviceCodeState.Success) state.userCode
            else ""
        }.distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private val _event = MutableSharedFlow<String>()
    val event = _event.asSharedFlow()

    init {
        getDeviceCode()
    }

    fun getDeviceCode(
        clientID: String = Config.Key.GITHUB_CLIENT_ID,
        scope: String = SCOPE_LIST.joinToString(" ")
    ) {
        viewModelScope.launch {
            getDeviceCodeUseCase(clientID, scope)
                .onStart { _deviceCodeState.update { DeviceCodeState.Loading } }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _deviceCodeState.update {
                                result.data.run {
                                    DeviceCodeState.Success(
                                        userCode = userCode,
                                        deviceCode = deviceCode,
                                        verificationUri = verificationUri
                                    )
                                }
                            }
                        }

                        is Result.Fail -> {
                            _deviceCodeState.update { DeviceCodeState.Error }
                            _event.emit(result.errorMessage())
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
            if (deviceCodeState.value is DeviceCodeState.Success) {
                val deviceCode = (deviceCodeState.value as DeviceCodeState.Success).deviceCode

                getAccessTokenUseCase(clientID, deviceCode, grantType)
                    .onStart { _accessTokenState.update { AccessTokenState.Loading } }
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _accessTokenState.update { AccessTokenState.Success(result.data) }
                            }


                            is Result.Fail.ResponseError -> {
                                _accessTokenState.update { AccessTokenState.Error }
                                _event.emit(result.errorMessage())
                            }

                            else -> {}
                        }
                    }
            }
        }
    }

    fun getVerificationUri(): String =
        if (deviceCodeState.value is DeviceCodeState.Success) {
            (deviceCodeState.value as DeviceCodeState.Success).verificationUri
        } else ""

    sealed class DeviceCodeState {
        data class Success(
            val userCode: String,
            val deviceCode: String,
            val verificationUri: String
        ) : DeviceCodeState()

        object None : DeviceCodeState()
        object Loading : DeviceCodeState()
        object Error : DeviceCodeState()
    }

    sealed class AccessTokenState {
        data class Success(
            val accessToken: String = ""
        ) : AccessTokenState()

        object None : AccessTokenState()
        object Loading : AccessTokenState()
        object Error : AccessTokenState()
    }

    companion object {

        private const val GRANT_TYPE = "urn:ietf:params:oauth:grant-type:device_code"

        // @see: https://docs.github.com/en/developers/apps/building-oauth-apps/scopes-for-oauth-apps#available-scopes
        private val SCOPE_LIST = arrayOf("repo", "user")
    }
}