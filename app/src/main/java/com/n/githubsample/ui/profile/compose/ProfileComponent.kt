package com.n.githubsample.ui.profile.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.n.githubsample.R
import com.n.githubsample.domain.model.MyPopularRepo
import com.n.githubsample.domain.model.User

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    user: User
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            AsyncImage(
                modifier = modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.white)),
                model = user.avatar_url,
                placeholder = rememberVectorPainter(image = Icons.Default.Person),
                contentDescription = null
            )
            Spacer(modifier = modifier.width(10.dp))

            Column(
                modifier = modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = user.name,
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.login,
                    color = colorResource(id = R.color.grey),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                tint = colorResource(id = R.color.grey)
            )
            Spacer(modifier = modifier.width(10.dp))

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.white))) {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(user.followers.toString())
                        }
                        append(" ")
                        append(stringResource(id = R.string.followers))
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(" · ")
                            append(user.following.toString())
                        }
                        append(" ")
                        append(stringResource(id = R.string.following))
                    }
                }
            )
        }

        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = colorResource(id = R.color.grey)
            )
            Spacer(modifier = modifier.width(10.dp))

        }
    }
}

@Preview
@Composable
fun UserProfilePreview() {
    UserProfile(
        user = User(
            name = "name",
            login = "login",
            followers = 1,
            following = 1
        )
    )
}

@Composable
fun RepositoryItem(
    modifier: Modifier = Modifier,
    repo: MyPopularRepo
) {
    Column(
        modifier = modifier
            .widthIn(min = 300.dp)
            .background(
                color = colorResource(id = R.color.arsenic),
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.grey),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.white)),
                model = repo.ownerProfileImage,
                placeholder = rememberVectorPainter(image = Icons.Default.Person),
                contentDescription = null
            )
            Spacer(modifier = modifier.width(8.dp))

            Text(text = repo.ownerName, fontSize = 14.sp, color = colorResource(id = R.color.grey))
        }
        Spacer(modifier = modifier.height(4.dp))

        Text(
            text = repo.repoName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )
        Spacer(modifier = modifier.height(30.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = colorResource(id = R.color.tangerine)
            )
            Spacer(modifier = modifier.width(8.dp))

            Text(
                text = repo.starCount.toString(),
                fontSize = 14.sp,
                color = colorResource(id = R.color.grey)
            )
            Spacer(modifier = modifier.width(12.dp))

            Box(
                modifier = modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(color = colorResource(id = R.color.amethyst))
            )
            Spacer(modifier = modifier.width(8.dp))

            Text(
                text = repo.repoLanguage,
                fontSize = 14.sp,
                color = colorResource(id = R.color.grey)
            )
            Spacer(modifier = modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun RepositoryItemPreview() {
    RepositoryItem(repo = MyPopularRepo("name", "", "title", "", 1, "kotlin"))
}

@Composable
fun UserRepositories(
    modifier: Modifier = Modifier,
    list: List<MyPopularRepo>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = colorResource(id = R.color.grey)
            )
            Spacer(modifier = modifier.width(10.dp))

            Text(
                text = stringResource(id = R.string.popular),
                color = colorResource(id = R.color.white),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = modifier.height(10.dp))

        LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            items(items = list) { repo ->
                RepositoryItem(repo = repo)
            }
        }
    }
}

@Preview
@Composable
fun UserRepositoriesPreview() {
    UserRepositories(
        list = listOf(
            MyPopularRepo("name", "", "title1", "", 1, "kotlin"),
            MyPopularRepo("name", "", "title2", "", 3, "kotlin")
        )
    )
}

@Composable
fun MenuRowItem(
    modifier: Modifier = Modifier,
    icon: Painter,
    iconColor: Color,
    title: String,
    count: Int? = null
) {
    Row(
        modifier = modifier
            .requiredHeightIn(min = 50.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconColor)
                .padding(6.dp),
            painter = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.white)
        )
        Text(
            modifier = modifier
                .weight(1F)
                .padding(8.dp),
            text = title,
            color = colorResource(id = R.color.white),
            fontSize = 16.sp
        )
        if (count != null) {
            Text(text = count.toString(), color = colorResource(id = R.color.grey))
        }
    }
}

@Preview
@Composable
fun MenuRowItemPreview() {
    MenuRowItem(
        icon = painterResource(id = R.drawable.ic_organization_50),
        iconColor = colorResource(id = R.color.sana),
        title = stringResource(id = R.string.organizations),
        count = 0
    )
}