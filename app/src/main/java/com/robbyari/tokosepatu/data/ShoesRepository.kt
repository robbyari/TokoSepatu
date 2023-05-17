package com.robbyari.tokosepatu.data

import com.robbyari.tokosepatu.model.FakeShoesDataSource
import com.robbyari.tokosepatu.model.OrderShoes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ShoesRepository {
    private val orderShoes = mutableListOf<OrderShoes>()

    init {
        if (orderShoes.isEmpty()) {
            FakeShoesDataSource.dummyShoes.forEach {
                orderShoes.add(OrderShoes(it, 0))
            }
        }
    }

    fun getAllShoes(): Flow<List<OrderShoes>> {
        return flowOf(orderShoes)
    }

    fun getOrderShoesById(shoesId: Long): OrderShoes {
        return orderShoes.first {
            it.shoes.id == shoesId
        }
    }

    fun updateOrderShoes(shoesId: Long, newSizeValue: Int): Flow<Boolean> {
        val index = orderShoes.indexOfFirst { it.shoes.id == shoesId }
        val result = if (index >= 0) {
            val orderShoess = orderShoes[index]
            orderShoes[index] =
                orderShoess.copy(shoes = orderShoess.shoes, size = newSizeValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderRewards(): Flow<List<OrderShoes>> {
        return getAllShoes()
            .map { orderShoes ->
                orderShoes.filter { orderShoes ->
                    orderShoes.size != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: ShoesRepository? = null

        fun getInstance(): ShoesRepository =
            instance ?: synchronized(this) {
                ShoesRepository().apply {
                    instance = this
                }
            }
    }
}