package com.n.githubsample.ui.profile.compose

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
                    .sizeIn(minWidth = 60.dp, minHeight = 60.dp)
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
fun MenuRowItem(
    modifier: Modifier = Modifier,
    @ColorRes
    colorRes: Int,
    @DrawableRes
    iconRes: Int,
    title: String,
    count: Int?
) {
    Row(
        modifier = modifier
            .requiredHeightIn(min = 50.dp)
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = colorRes))
                .padding(4.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null
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
        colorRes = R.color.sana,
        iconRes = R.drawable.ic_organization_50,
        title = stringResource(id = R.string.organizations),
        count = 0
    )
}