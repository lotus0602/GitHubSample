package com.n.githubsample.data.model

import com.n.githubsample.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val avatar_url: String,
    val bio: String?,
    val blog: String,
    val collaborators: Int,
    val company: String?,
    val created_at: String,
    val email: String? = "",
    val events_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val hireable: Boolean?,
    val html_url: String,
    val id: Long,
    val location: String?,
    val login: String,
    val name: String,
    val node_id: String,
    val organizations_url: String,
    val owned_private_repos: Int,
    val private_gists: Int,
    val public_gists: Int,
    val public_repos: Int,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val total_private_repos: Int,
    val twitter_username: String?,
    val type: String,
    val updated_at: String,
    val url: String
) {
    fun toUser(): User =
        User(
//            id = id,
            login = login,
            name = name,
            email = email ?: "",
            avatar_url = avatar_url,
            followers = followers,
            following = following,
            repositoryCount = public_repos + total_private_repos
        )
}