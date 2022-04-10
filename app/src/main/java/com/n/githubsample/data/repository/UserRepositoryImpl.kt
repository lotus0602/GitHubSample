package com.n.githubsample.data.repository

import com.n.githubsample.data.api.UserService
import com.n.githubsample.domain.model.User
import com.n.githubsample.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: UserService
) : UserRepository {
    override suspend fun getAuthUser(accessToken: String): User {
        return service.getAuthUser(accessToken).toUser()
    }

    override suspend fun getUser(userName: String) = flow<User> {
        service.getUser(userName).map { it.toUser() }
    }
}