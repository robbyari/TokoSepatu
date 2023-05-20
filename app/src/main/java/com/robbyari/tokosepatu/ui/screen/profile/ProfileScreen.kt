package com.robbyari.tokosepatu.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robbyari.tokosepatu.R
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.foto_profil),
            contentDescription = "Photo Profile",
            modifier = Modifier
                .clip(CircleShape)
                .size(140.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = "Robby Ari Wibowo",
            color = Color.White
        )
        Text(
            text = "robbyariw23@gmail.com",
            color = Color.White
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    TokoSepatuTheme {
        ProfileScreen()
    }
}