package com.n.githubsample.domain.usecase

import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.Repo
import com.n.githubsample.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepoUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) {

    operator fun invoke(): Flow<Result<List<Repo>>> = flow {
        try {
            emit(Result.Loading)
            val res = repoRepository.getPublicRepo()
            emit(Result.Success(res))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: ""))
        }
    }
}