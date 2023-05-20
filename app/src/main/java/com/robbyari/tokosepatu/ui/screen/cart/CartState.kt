package com.robbyari.tokosepatu.ui.screen.cart

import com.robbyari.tokosepatu.model.OrderShoes

data class CartState(
    val orderShoes: List<OrderShoes>,
    val totalRequiredPrice: Int
)
