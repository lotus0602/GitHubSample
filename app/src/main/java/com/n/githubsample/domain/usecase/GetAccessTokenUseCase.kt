package com.n.githubsample.domain.usecase

import android.util.Log
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
            if (result.isSuccess()) {
                emit(Result.Success("${result.tokenType} ${result.accessToken}"))
            } else {
                emit(Result.Fail.ResponseError(result.error, result.errorDescription))
            }

        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("TAG", "HttpException: ${e.response()?.errorBody()?.string()}")

            emit(Result.Fail.NetworkError(e))
        } catch (e: Exception) {
            e.printStackTrace()

            emit(Result.Fail.Error(e))
        }
    }
}