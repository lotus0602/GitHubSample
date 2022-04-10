package com.n.githubsample.data.api

import com.n.githubsample.data.model.UserDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserService {

    @Headers("Accept: application/json")
    @GET("user")
    suspend fun getAuthUser(@Header("Authorization") accessToken: String): UserDTO

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") userName: String): Flow<UserDTO>
}