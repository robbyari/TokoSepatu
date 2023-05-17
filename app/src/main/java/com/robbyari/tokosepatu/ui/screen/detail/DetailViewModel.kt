package com.robbyari.tokosepatu.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robbyari.tokosepatu.data.ShoesRepository
import com.robbyari.tokosepatu.model.OrderShoes
import com.robbyari.tokosepatu.model.Shoes
import com.robbyari.tokosepatu.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ShoesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderShoes>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<OrderShoes>>
        get() = _uiState

    fun getShoesId(shoesId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderShoesById(shoesId))
        }
    }

    fun addToCart(shoes: Shoes, size: Int) {
        viewModelScope.launch {
            repository.updateOrderShoes(shoes.id, size)
        }
    }
}