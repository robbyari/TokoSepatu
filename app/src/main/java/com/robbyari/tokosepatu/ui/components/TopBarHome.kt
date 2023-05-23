package com.robbyari.tokosepatu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robbyari.tokosepatu.R
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme

@Composable
fun TopBarHome(
    modifier: Modifier = Modifier.padding(5.dp),
    navigateToProfile: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_dicoding),
            contentDescription = null,
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape),
        )
        Column(
            modifier = modifier.weight(1f),
        ) {
            Text(
                text = stringResource(R.string.selamat_datang),
                color = colorResource(id = R.color.white)
            )
            Text(
                text = stringResource(R.string.jetpack_compose),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.white)
            )
        }
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = stringResource(R.string.about_page),
            modifier = modifier
                .size(30.dp)
                .clickable { navigateToProfile() },
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarHomePreview() {
    TokoSepatuTheme {
        TopBarHome(navigateToProfile = {})
    }
}

