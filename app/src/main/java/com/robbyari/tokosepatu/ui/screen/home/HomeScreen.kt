package com.robbyari.tokosepatu.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.robbyari.tokosepatu.di.Injection
import com.robbyari.tokosepatu.model.OrderShoes
import com.robbyari.tokosepatu.ui.ViewModelFactory
import com.robbyari.tokosepatu.ui.common.UiState
import com.robbyari.tokosepatu.ui.components.ShoesItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllShoes()
            }

            is UiState.Success -> {
                HomeContent(
                    orderShoes = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderShoes: List<OrderShoes>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
   LazyVerticalGrid(
       columns = GridCells.Adaptive(160.dp),
       contentPadding = PaddingValues(16.dp),
       horizontalArrangement = Arrangement.spacedBy(16.dp),
       verticalArrangement = Arrangement.spacedBy(20.dp),
       modifier = modifier
   ) {
       items(orderShoes) { data ->
           ShoesItem(
               image = data.shoes.image,
               title = data.shoes.title,
               price = data.shoes.price,
               rating = data.shoes.rating,
               modifier = Modifier.clickable {
                   navigateToDetail(data.shoes.id)
               }
           )
       }
   }
}

