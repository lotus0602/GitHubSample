package com.n.githubsample.domain.usecase

import com.n.githubsample.domain.Result
import com.n.githubsample.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(
        clientID: String,
        deviceCode: String,
        grantType: String
    ): Flow<Result<String>> = flow {
        try {
            val result = authRepository.getAccessToken(clientID, deviceCode, grantType)
            emit(Result.Success("${result.tokenType} ${result.accessToken}"))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: ""))
        }
    }
}