package com.n.githubsample.ui.profile.compose

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.n.githubsample.R

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