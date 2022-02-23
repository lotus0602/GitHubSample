package com.n.githubsample.di

import com.n.githubsample.data.api.RepoService
import com.n.githubsample.data.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideRepoService(retrofit: Retrofit): RepoService =
        retrofit.create(RepoService::class.java)
}