package com.n.githubsample.ui.login.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.n.githubsample.R

@Composable
fun StartSignInScreen(
    modifier: Modifier = Modifier,
    onClickSignIn: () -> Unit
) {
    StartSignInScreenContent(
        modifier = modifier,
        onClickSignIn = onClickSignIn
    )
}

@Composable
internal fun StartSignInScreenContent(
    modifier: Modifier = Modifier,
    onClickSignIn: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.github_mark_light_120px_plus),
                contentDescription = null
            )
        }

        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = onClickSignIn
        ) {
            Text(text = stringResource(id = R.string.sign_in))
        }
    }
}

@Preview
@Composable
fun StartSignInScreenPreview() {
    StartSignInScreenContent(onClickSignIn = {})
}