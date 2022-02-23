package com.n.githubsample.data.api

import com.n.githubsample.data.model.UserDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") userName: String): Flow<UserDTO>
}