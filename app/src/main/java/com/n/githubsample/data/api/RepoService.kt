package com.n.githubsample.data.api

import com.n.githubsample.data.model.RepoDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoService {

    @GET("/users/{username}/repos")
    suspend fun getMyRepo(
        @Path("username") userName: String,
        @Query("type") type: String,
        @Query("sort") sort: String,
        @Query("direction") direction: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
    ): List<RepoDTO>

    @GET("/repositories")
    suspend fun getPublicRepo(): List<RepoDTO>
}