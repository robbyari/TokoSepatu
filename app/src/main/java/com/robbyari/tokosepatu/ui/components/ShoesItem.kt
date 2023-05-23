package com.robbyari.tokosepatu.ui.components

import android.content.res.Configuration
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robbyari.tokosepatu.R
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme

@Composable
fun ShoesItem(
    image: Int,
    title: String,
    price: String,
    rating: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = colorResource(id = R.color.grey)),
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = modifier
                    .size(180.dp)
                    .rotate(-15f)
                    .offset(-10.dp, -10.dp)
            )
            TextButton(
                onClick = {},
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = modifier
                        .size(30.dp)
                        .clickable { Log.d("testklik", "Favorite") },
                    tint = Color.White
                )
            }
        }
        Row(
            modifier = Modifier.align(Alignment.Start),
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = modifier.size(20.dp),
                tint = Color.Yellow
            )
            Text(
                text = rating,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier

            )
        }
        Text(
            text = "Rp $price",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .size(155.dp, 20.dp)
                .align(Alignment.Start)
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShoesItemPreview() {
    TokoSepatuTheme {
        Surface {
            ShoesItem(image = R.drawable.air_force_1_sculpt_shoes, title = "Air Force 1 Sculp Shoes", price = "2.450.000", rating = "4.5")
        }
    }
}