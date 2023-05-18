package com.robbyari.tokosepatu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robbyari.tokosepatu.TokoSepatuViewModel
import com.robbyari.tokosepatu.data.ShoesRepository
import com.robbyari.tokosepatu.ui.screen.cart.CartViewModel
import com.robbyari.tokosepatu.ui.screen.detail.DetailViewModel
import com.robbyari.tokosepatu.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: ShoesRepository) :
ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TokoSepatuViewModel::class.java)) {
            return TokoSepatuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}