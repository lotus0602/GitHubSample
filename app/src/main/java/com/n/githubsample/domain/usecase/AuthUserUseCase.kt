package com.n.githubsample.domain.usecase

import android.util.Log
import com.n.githubsample.domain.Result
import com.n.githubsample.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

//    operator fun invoke(
//        clientID: String,
//        scope: String,
//        grantType: String = "urn:ietf:params:oauth:grant-type:device_code"
//    ): Flow<Result<Any>> = flow {
//        try {
//            emit(Result.Loading)
//
//            val getDeviceCodeRes = authRepository.getDeviceCode(clientID, scope)
//            val deviceCode = getDeviceCodeRes.deviceCode
////            emit(Result.Process(getDeviceCodeRes))
//
//            flow {
//                val getAccessTokenRes = authRepository.getAccessToken(clientID, deviceCode, grantType)
//                val accessToken = getAccessTokenRes.access_token
//                emit(Result.Success(accessToken))
//            }.retryWhen { cause, attempt ->
//                cause.printStackTrace()
//                false
////                if (attempt < 6) {
////                    delay(10_000)
////                    true
////                } else false
//            }.collect()
//
//        } catch (e: Exception) {
//            emit(Result.Error(e.message ?: ""))
//        }
//    }
}