package com.n.githubsample.di

import com.n.githubsample.domain.repository.AuthRepository
import com.n.githubsample.domain.repository.RepoRepository
import com.n.githubsample.domain.repository.UserRepository
import com.n.githubsample.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetDeviceCodeUseCase(authRepository: AuthRepository) =
        GetDeviceCodeUseCase(authRepository)

    @Provides
    fun provideGetAccessTokenUseCase(authRepository: AuthRepository) =
        GetAccessTokenUseCase(authRepository)

    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository) = GetUserUseCase(userRepository)

    @Provides
    fun provideGetMyRepoUseCase(repoRepository: RepoRepository) = GetMyRepoUseCase(repoRepository)

    @Provides
    fun provideGetRepoUseCase(repoRepository: RepoRepository) = GetRepoUseCase(repoRepository)
}