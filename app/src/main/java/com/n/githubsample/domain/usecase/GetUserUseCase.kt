package com.n.githubsample.domain.usecase

import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.User
import com.n.githubsample.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(
        accessToken: String
    ): Flow<Result<User>> = flow {
        try {
            val result = userRepository.getAuthUser(accessToken)
            emit(Result.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Fail.Error(e))
        }
    }
}