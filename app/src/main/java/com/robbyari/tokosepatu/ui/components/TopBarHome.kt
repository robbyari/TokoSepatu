package com.robbyari.tokosepatu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ColorScheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robbyari.tokosepatu.R
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme

@Composable
fun TopBarHome(
    modifier: Modifier = Modifier.padding(5.dp)
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
                text = "Selamat Datang",
                color = colorResource(id = R.color.white)
            )
            Text(
                text = "Robby Ari Wibowo",
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.white)
            )
        }
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            modifier = modifier
                .size(30.dp),
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarHomePreview() {
    TokoSepatuTheme {
        TopBarHome()
    }
}

