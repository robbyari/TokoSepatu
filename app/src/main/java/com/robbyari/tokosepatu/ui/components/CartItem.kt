package com.robbyari.tokosepatu.ui.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robbyari.tokosepatu.R
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme
import com.robbyari.tokosepatu.ui.theme.Typography

@Composable
fun CartItem(
    shoesId: Long,
    image: Int,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color = colorResource(id = R.color.grey))
                .padding(20.dp),
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = modifier
                    .size(40.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = Typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
            Text(
                text = "Rp $totalPrice",
                color = Color.White,
                style = Typography.labelMedium
            )
        }
        ProductCounter(
            orderId = shoesId,
            orderCount = count,
            onProductIncreased = {onProductCountChanged(shoesId, count + 1)},
            onProductDecreased = {onProductCountChanged(shoesId, count - 1)},
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
fun CartItemPreview() {
    TokoSepatuTheme() {
        CartItem(shoesId = 1, image = R.drawable.air_force_1_sculpt_shoes, title = "Air Force 1 Sculp Series White", totalPrice = 2400000, count = 0, onProductCountChanged = {shoesId, count ->})
    }
}