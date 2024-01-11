package com.n.githubsample.ui.login.compose

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.n.githubsample.R
import com.n.githubsample.ui.login.AccessTokenUiState

@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    uiState: AccessTokenUiState,
    onClickComplete: () -> Unit
) {
    AuthenticationScreenContent(
        modifier = modifier,
        uiState = uiState,
        onClickComplete = onClickComplete
    )
}

@Composable
internal fun AuthenticationScreenContent(
    modifier: Modifier = Modifier,
    uiState: AccessTokenUiState,
    onClickComplete: () -> Unit
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
            when (uiState) {
                AccessTokenUiState.None -> {
                    Text(
                        text = stringResource(id = R.string.click_complete),
                        color = colorResource(id = R.color.white)
                    )
                }

                AccessTokenUiState.Loading -> {
                    LoadingView()
                }

                is AccessTokenUiState.Error -> {
                    Text(
                        text = uiState.errorType,
                        color = colorResource(id = R.color.white),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = uiState.errorMessage,
                        color = colorResource(id = R.color.white),
                        fontSize = 16.sp
                    )
                }

                is AccessTokenUiState.Success -> {
                    Text(
                        text = stringResource(id = R.string.success),
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }

        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = onClickComplete
        ) {
            Text(text = stringResource(id = R.string.complete_verification))
        }
    }
}

@Preview
@Composable
fun AuthenticationScreenPreview() {
    AuthenticationScreenContent(
        uiState = AccessTokenUiState.Success(""),
        onClickComplete = {}
    )
}