package com.robbyari.tokosepatu

import androidx.lifecycle.ViewModel
import com.robbyari.tokosepatu.data.ShoesRepository
import kotlinx.coroutines.flow.MutableStateFlow

class TokoSepatuViewModel(private val repository: ShoesRepository) : ViewModel() {
    fun search(newQuery: String) {

    }
}