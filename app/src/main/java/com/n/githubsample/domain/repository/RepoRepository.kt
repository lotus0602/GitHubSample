package com.n.githubsample.domain.repository

import com.n.githubsample.domain.model.Repo

interface RepoRepository {

    suspend fun getPublicRepo(): List<Repo>
}