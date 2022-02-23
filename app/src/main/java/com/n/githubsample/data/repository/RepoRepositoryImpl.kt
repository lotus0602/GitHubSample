package com.n.githubsample.data.repository

import com.n.githubsample.data.api.RepoService
import com.n.githubsample.domain.model.Repo
import com.n.githubsample.domain.repository.RepoRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val service: RepoService
) : RepoRepository {

    //    override suspend fun getPublicRepo() = flow {
//        val res = service.getPublicRepo().map { it.toRepo() }
//        emit(res)
//    }
    override suspend fun getPublicRepo(): List<Repo> =
        service.getPublicRepo().map { it.toRepo() }
}