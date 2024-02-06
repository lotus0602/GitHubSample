package com.n.githubsample.ui.setting.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.n.githubsample.R

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onClickSignOut: () -> Unit
) {
    SettingsScreenContent(
        modifier = modifier,
        onClickSignOut = onClickSignOut
    )
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onClickSignOut: () -> Unit
) {
    Column(modifier.fillMaxSize()) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 20.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = stringResource(id = R.string.more_options),
            color = colorResource(id = R.color.grey),
            fontSize = 18.sp
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 20.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .clickable { onClickSignOut() },
            text = stringResource(id = R.string.sign_out),
            color = colorResource(id = R.color.white),
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreenContent(onClickSignOut = {})
}