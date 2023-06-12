package com.n.githubsample.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.n.githubsample.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
            .apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

        return OkHttpClient.Builder()
            .apply {
                addNetworkInterceptor(logging)
            }.build()
    }

    @Singleton
    @Provides
    fun provideKotlinSerializationConverter(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            coerceInputValues = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)

    }

    @Singleton
    @Provides
    @Named("GitHubRetrofit")
    fun provideGitHubRetrofit(
        client: OkHttpClient,
        kotlinSerializationConverter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .apply {
                baseUrl(Config.Common.GITHUB_URL)
                client(client)
            }.apply {
                addConverterFactory(kotlinSerializationConverter)
            }.build()

    @Singleton
    @Provides
    fun provideGitHubApiRetrofit(
        client: OkHttpClient,
        kotlinSerializationConverter: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .apply {
                baseUrl(Config.Api.BASE_URL)
                client(client)
            }.apply {
                addConverterFactory(kotlinSerializationConverter)
            }.build()
}