package com.robbyari.tokosepatu.ui.screen.detail

import android.icu.text.CaseMap.Title
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robbyari.tokosepatu.R
import com.robbyari.tokosepatu.di.Injection
import com.robbyari.tokosepatu.ui.ViewModelFactory
import com.robbyari.tokosepatu.ui.common.UiState
import com.robbyari.tokosepatu.ui.components.OrderButton
import com.robbyari.tokosepatu.ui.components.ProductCounter
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme

@Composable
fun DetailScreen(
    shoesId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getShoesId(shoesId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.shoes.image,
                    title = data.shoes.title,
                    description = data.shoes.description,
                    basePrice = data.shoes.price,
                    count = data.count,
                    rating = data.shoes.rating,
                    onBackClick = { navigateBack },
                    onAddToCart = { count ->
                        viewModel.addToCart(data.shoes, count)
                        navigateToCart
                    }
                )

            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    description: String,
    basePrice: Int,
    count: Int,
    rating: String,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var totalPrice by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() },
                tint = Color.White
            )
            Box(
                modifier = Modifier
                    .offset(40.dp, 0.dp)
                    .size(350.dp)
                    .clip(RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp))
                    .background(color = colorResource(id = R.color.grey))

            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    modifier = modifier
                        .size(320.dp)
                        .align(Alignment.Center)
                        .offset(-30.dp, -10.dp)
                        .rotate(-30f)
                )
            }
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row() {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                    Text(
                        text = rating,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                        color = Color.White
                    )
                }

                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.required_price, basePrice),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color.White
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.offset(0.dp, 20.dp),
                    color = Color.White
                )
            }
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProductCounter(
                orderId = 1,
                orderCount = orderCount,
                onProductIncreased = {orderCount++},
                onProductDecreased = {if (orderCount > 0) orderCount--},
                modifier = modifier
            )
            totalPrice = basePrice * orderCount
            OrderButton(
                text = stringResource(id = R.string.add_to_cart, totalPrice),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                },
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    TokoSepatuTheme() {
        Surface() {
            DetailContent(image = R.drawable.air_force_1_sculpt_shoes, title = "Nike Air Force 1", description = "From hoops staple to urban street legend, the Nike AF-1 React takes another step forward into shoe iconography. Amplified features from the outsole to the branding add dramatic expression to the storied look while Nike React tech assists with a smooth ride. Stand out to fit in.", basePrice = 2450000, count = 1, onBackClick = {}, rating = "4.5",onAddToCart = {})
        }
    }
}