package com.n.githubsample.domain.repository

import com.n.githubsample.domain.model.MyPopularRepo
import com.n.githubsample.domain.model.Repo

interface RepoRepository {

    suspend fun getMyRepo(
        userName: String,
        type: String,
        sort: String,
        direction: String,
        per_page: Int,
        page: Int
    ): List<MyPopularRepo>
    suspend fun getPublicRepo(): List<Repo>
}