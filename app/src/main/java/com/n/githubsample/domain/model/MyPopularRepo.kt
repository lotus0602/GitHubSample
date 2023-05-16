package com.n.githubsample.domain.model

data class MyPopularRepo(
    val ownerName: String,
    val ownerProfileImage: String,
    val repoName: String,
    val repoDescription: String,
    val starCount: Int,
    val repoLanguage: String
)