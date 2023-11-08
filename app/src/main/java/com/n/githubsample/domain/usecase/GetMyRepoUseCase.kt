package com.n.githubsample.domain.usecase

import com.n.githubsample.domain.Result
import com.n.githubsample.domain.model.MyPopularRepo
import com.n.githubsample.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyRepoUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) {

    operator fun invoke(
        userName: String,
        type: String = "all",
        sort: String = "updated",
        direction: String = "desc",
        per_page: Int = 30,
        page: Int = 1
    ): Flow<Result<List<MyPopularRepo>>> = flow {
        try {
            val res = repoRepository.getMyRepo(userName, type, sort, direction, per_page, page)
            emit(Result.Success(res))
        } catch (e: Exception) {
            emit(Result.Fail.Error(e))
        }
    }
}