package com.robbyari.tokosepatu.di

import com.robbyari.tokosepatu.data.ShoesRepository

object Injection {
    fun provideRepository(): ShoesRepository {
        return ShoesRepository.getInstance()
    }
}