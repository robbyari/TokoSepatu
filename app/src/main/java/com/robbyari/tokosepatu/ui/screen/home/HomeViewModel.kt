package com.robbyari.tokosepatu.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robbyari.tokosepatu.data.ShoesRepository
import com.robbyari.tokosepatu.model.OrderShoes
import com.robbyari.tokosepatu.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ShoesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderShoes>>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<OrderShoes>>>
        get() = _uiState

    fun getAllShoes() {
        viewModelScope.launch {
            repository.getAllShoes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderShoes ->
                    _uiState.value = UiState.Success(orderShoes)
                }
        }
    }

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchShoes(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderShoes ->
                    _uiState.value = UiState.Success(orderShoes.sortedBy { it.shoes.title })
                }
        }
    }

}