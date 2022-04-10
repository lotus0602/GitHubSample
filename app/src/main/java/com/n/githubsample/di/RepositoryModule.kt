package com.n.githubsample.di

import com.n.githubsample.data.api.AuthService
import com.n.githubsample.data.api.RepoService
import com.n.githubsample.data.api.UserService
import com.n.githubsample.data.repository.AuthRepositoryImpl
import com.n.githubsample.data.repository.RepoRepositoryImpl
import com.n.githubsample.data.repository.UserRepositoryImpl
import com.n.githubsample.domain.repository.AuthRepository
import com.n.githubsample.domain.repository.RepoRepository
import com.n.githubsample.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(authService: AuthService): AuthRepository =
        AuthRepositoryImpl(authService)

    @Singleton
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository =
        UserRepositoryImpl(userService)

    @Singleton
    @Provides
    fun provideRepoRepository(repoService: RepoService): RepoRepository =
        RepoRepositoryImpl(repoService)
}