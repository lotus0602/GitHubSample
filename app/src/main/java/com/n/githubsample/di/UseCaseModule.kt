package com.n.githubsample.di

import com.n.githubsample.domain.repository.RepoRepository
import com.n.githubsample.domain.usecase.GetRepoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetRepoUseCase(repoRepository: RepoRepository) = GetRepoUseCase(repoRepository)
}