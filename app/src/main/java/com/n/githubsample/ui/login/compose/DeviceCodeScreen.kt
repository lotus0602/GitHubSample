package com.n.githubsample.ui.login.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.n.githubsample.ui.login.DeviceCodeUiState

@Composable
fun DeviceCodeScreen(
    modifier: Modifier = Modifier,
    uiState: DeviceCodeUiState,
    onClickVerification: (uri: String) -> Unit
) {
    DeviceCodeScreenContent(
        modifier = modifier,
        uiState = uiState,
        onClickVerification = onClickVerification
    )
}

@Composable
internal fun DeviceCodeScreenContent(
    modifier: Modifier = Modifier,
    uiState: DeviceCodeUiState,
    onClickVerification: (uri: String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            DeviceCodeUiState.Loading -> LoadingView(modifier = modifier)
            is DeviceCodeUiState.Error ->
                DeviceCodeErrorContent(
                    modifier = modifier,
                    error = uiState
                )

            is DeviceCodeUiState.Success ->
                DeviceCodeContent(
                    modifier = modifier,
                    uiState = uiState,
                    onClickVerification = onClickVerification
                )
        }
    }
}

@Composable
internal fun DeviceCodeContent(
    modifier: Modifier = Modifier,
    uiState: DeviceCodeUiState.Success,
    onClickVerification: (uri: String) -> Unit
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
            Text(
                text = stringResource(id = R.string.your_device_code),
                color = colorResource(id = R.color.white),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = uiState.userCode,
                color = colorResource(id = R.color.white),
                fontSize = 16.sp
            )
        }

        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = { onClickVerification(uiState.verificationUri) }
        ) {
            Text(text = stringResource(id = R.string.go_to_verification))
        }
    }
}

@Composable
internal fun DeviceCodeErrorContent(
    modifier: Modifier = Modifier,
    error: DeviceCodeUiState.Error
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
    ) {
        Text(
            text = error.errorType,
            color = colorResource(id = R.color.white),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = error.errorMessage,
            color = colorResource(id = R.color.white),
            fontSize = 16.sp
        )
    }
}

@Composable
internal fun LoadingView(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.size(100.dp, 100.dp)
    )
}

@Preview
@Composable
fun DeviceCodeContentPreview() {
    DeviceCodeContent(
        uiState = DeviceCodeUiState.Success("userCode", "deviceCode", "uri"),
        onClickVerification = {}
    )
}

@Preview
@Composable
fun DeviceCodeErrorContentPreview() {
    DeviceCodeErrorContent(
        error = DeviceCodeUiState.Error("ErrorType", "Error Message")
    )
}

@Preview
@Composable
fun LoadingViewPreview() {
    LoadingView()
}