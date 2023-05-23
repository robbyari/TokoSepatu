package com.robbyari.tokosepatu.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robbyari.tokosepatu.data.ShoesRepository
import com.robbyari.tokosepatu.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: ShoesRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderShoes() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderShoes()
                .collect { orderShoes ->
                    val totalRequiredPrice =
                        orderShoes.sumOf { it.shoes.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderShoes, totalRequiredPrice))
                }
        }
    }

    fun updateOrderShoes(shoesId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderShoes(shoesId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderShoes()
                    }

                }
        }
    }
}