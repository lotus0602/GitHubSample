package com.n.githubsample.ui.profile.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n.githubsample.R
import com.n.githubsample.domain.model.MyPopularRepo
import com.n.githubsample.domain.model.User

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User,
    list: List<MyPopularRepo>
) {
    ProfileScreenContent(modifier, user, list)
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    user: User,
    list: List<MyPopularRepo>
) {
    Column(modifier = modifier) {
        UserProfile(user = user)
        Spacer(modifier = modifier.height(10.dp))

        UserRepositories(list = list)
        Spacer(modifier = modifier.height(10.dp))
        Divider(color = colorResource(id = R.color.grey))

        MenuRowItem(
            colorRes = R.color.arsenic,
            iconRes = R.drawable.ic_repository_50,
            title = stringResource(id = R.string.repositories),
            count = 0
        )
        MenuRowItem(
            colorRes = R.color.sana,
            iconRes = R.drawable.ic_organization_50,
            title = stringResource(id = R.string.organizations),
            count = 0
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreenContent(
        user = User(
            name = "name",
            login = "login",
            followers = 1,
            following = 1
        ),
        list = listOf(
            MyPopularRepo("name", "", "title1", "", 1, "kotlin"),
            MyPopularRepo("name", "", "title2", "", 3, "kotlin")
        )
    )
}