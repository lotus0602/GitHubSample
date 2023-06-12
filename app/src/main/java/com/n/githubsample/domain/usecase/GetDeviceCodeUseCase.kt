package com.n.githubsample.domain.usecase

import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.DeviceCode
import com.n.githubsample.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDeviceCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(
        clientID: String,
        scope: String
    ): Flow<Result<DeviceCode>> = flow {
        try {
            val result = authRepository.getDeviceCode(clientID, scope)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message ?: ""))
        }
    }
}