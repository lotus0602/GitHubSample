package com.n.githubsample.ui.profile.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n.githubsample.R
import com.n.githubsample.domain.model.MyPopularRepo
import com.n.githubsample.domain.model.User
import com.n.githubsample.ui.login.compose.LoadingView
import com.n.githubsample.ui.profile.ProfileUiState

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState
) {
    ProfileScreenContent(
        modifier = modifier,
        uiState = uiState
    )
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState
) {
    when (uiState) {
        is ProfileUiState.Loading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingView(modifier)
            }
        }

        is ProfileUiState.Success -> {
            Column(modifier = modifier.fillMaxSize()) {
                UserProfile(user = uiState.user)
                Spacer(modifier = modifier.height(10.dp))

                UserRepositories(list = uiState.repositories)
                Spacer(modifier = modifier.height(10.dp))
                Divider(color = colorResource(id = R.color.grey))

                MenuRowItem(
                    icon = painterResource(id = R.drawable.ic_repository_50),
                    iconColor = colorResource(id = R.color.arsenic),
                    title = stringResource(id = R.string.repositories),
                    count = uiState.user.repositoryCount
                )
                MenuRowItem(
                    icon = painterResource(id = R.drawable.ic_organization_50),
                    iconColor = colorResource(id = R.color.sana),
                    title = stringResource(id = R.string.organizations)
                )
                MenuRowItem(
                    icon = rememberVectorPainter(image = Icons.TwoTone.Star),
                    iconColor = colorResource(id = R.color.tangerine),
                    title = stringResource(id = R.string.starred)
                )
            }
        }

        is ProfileUiState.Error -> {

        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreenContent(
        uiState = ProfileUiState.Success(
            user = User(
                name = "name",
                login = "login",
                followers = 1,
                following = 1
            ),
            repositories = listOf(
                MyPopularRepo("name", "", "title1", "", 1, "kotlin"),
                MyPopularRepo("name", "", "title2", "", 3, "kotlin")
            )
        )
    )
}