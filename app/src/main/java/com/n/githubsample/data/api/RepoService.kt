package com.n.githubsample.data.api

import com.n.githubsample.data.model.RepoDTO
import retrofit2.http.GET

interface RepoService {

    @GET("/repositories")
    suspend fun getPublicRepo(): List<RepoDTO>
}