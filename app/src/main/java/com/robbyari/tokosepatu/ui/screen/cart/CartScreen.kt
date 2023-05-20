package com.robbyari.tokosepatu.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robbyari.tokosepatu.R
import com.robbyari.tokosepatu.di.Injection
import com.robbyari.tokosepatu.ui.ViewModelFactory
import com.robbyari.tokosepatu.ui.common.UiState
import com.robbyari.tokosepatu.ui.components.CartItem
import com.robbyari.tokosepatu.ui.components.OrderButton
import com.robbyari.tokosepatu.ui.navigation.Screen

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderShoes()
            }

            is UiState.Success -> {
                CartContent(
                    state = uiState.data,
                    onProductCountChanged = { shoesId, count ->
                        viewModel.updateOrderShoes(shoesId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(R.string.share_message, state.orderShoes.count(), state.totalRequiredPrice)
    Column(modifier = modifier.fillMaxSize()) {
        OrderButton(
            text = stringResource(id = R.string.total_order, state.totalRequiredPrice),
            enabled = state.orderShoes.isNotEmpty(),
            modifier = Modifier.padding(16.dp),
            onClick = {
                onOrderButtonClicked(shareMessage)
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.orderShoes, key = { it.shoes.id}) {item ->
                CartItem(
                    shoesId = item.shoes.id,
                    image = item.shoes.image,
                    title = item.shoes.title,
                    totalPrice = item.shoes.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged
                )
                Divider(
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}