package com.n.githubsample.data.repository

import com.n.githubsample.data.api.RepoService
import com.n.githubsample.domain.model.MyPopularRepo
import com.n.githubsample.domain.model.Repo
import com.n.githubsample.domain.repository.RepoRepository
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val service: RepoService
) : RepoRepository {

    override suspend fun getMyRepo(
        userName: String,
        type: String,
        sort: String,
        direction: String,
        per_page: Int,
        page: Int,
    ): List<MyPopularRepo> =
        service.getMyRepo(userName, type, sort, direction, per_page, page).map { it.toPopularRepo() }

    override suspend fun getPublicRepo(): List<Repo> =
        service.getPublicRepo().map { it.toRepo() }
}