package com.n.githubsample.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.n.githubsample.R
import com.n.githubsample.utils.IntentKit

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: GitHubAuthVM = viewModel()
) {
    val userCode = viewModel.userCode.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = BiasAlignment.Vertical(-0.5f)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.your_device_code),
                color = colorResource(id = R.color.white),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = userCode.value,
                color = colorResource(id = R.color.white),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                IntentKit.browseIntent(context, viewModel.getVerificationUri())
            }) {
                Text(text = stringResource(id = R.string.go_to_verification))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                viewModel.getAccessToken()
            }) {
                Text(text = stringResource(id = R.string.complete_verification))
            }
        }
    }
}

@Composable
fun AuthScreenPreview_UI(
    modifier: Modifier = Modifier
) {
    val userCode = "userCode"
    val url = ""
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = BiasAlignment.Vertical(-0.5f)
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.your_device_code),
                color = colorResource(id = R.color.white),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = userCode,
                color = colorResource(id = R.color.white),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                IntentKit.browseIntent(context, url)
            }) {
                Text(text = stringResource(id = R.string.go_to_verification))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {

            }) {
                Text(text = stringResource(id = R.string.complete_verification))
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreenPreview_UI()
}