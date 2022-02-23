package com.n.githubsample.domain.repository

import com.n.githubsample.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(userName: String): Flow<User>
}