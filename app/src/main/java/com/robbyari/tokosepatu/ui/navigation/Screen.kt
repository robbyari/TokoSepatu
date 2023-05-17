package com.robbyari.tokosepatu.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Favorite : Screen("favorite")

    object DetailShoes : Screen("home/{shoesId}") {
        fun createRoute(shoesId: Long) = "home/$shoesId"
    }
}
