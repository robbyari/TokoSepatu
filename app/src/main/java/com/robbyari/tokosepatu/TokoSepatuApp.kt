package com.robbyari.tokosepatu

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.robbyari.tokosepatu.ui.components.TopBarHome
import com.robbyari.tokosepatu.ui.navigation.NavigationItem
import com.robbyari.tokosepatu.ui.navigation.Screen
import com.robbyari.tokosepatu.ui.screen.cart.CartScreen
import com.robbyari.tokosepatu.ui.screen.detail.DetailScreen
import com.robbyari.tokosepatu.ui.screen.home.HomeScreen
import com.robbyari.tokosepatu.ui.screen.profile.ProfileScreen
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokoSepatuApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != Screen.DetailShoes.route) {
                TopBarHome(
                    navigateToProfile = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            if (currentRoute != Screen.DetailShoes.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier,
        containerColor = Color.Transparent,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { shoesId ->
                        navController.navigate(Screen.DetailShoes.createRoute(shoesId))
                    }
                )
            }
            composable(Screen.Cart.route) {
                val context = LocalContext.current
                CartScreen(
                    onOrderButtonClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailShoes.route,
                arguments = listOf(navArgument("shoesId") { type = NavType.LongType })
            ) {
                val id = it.arguments?.getLong("shoesId") ?: -1L
                DetailScreen(
                    shoesId = id,
                    navigateBack = {
                        navController.navigate(Screen.Home.route)
                    },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
            .padding(50.dp, 0.dp, 50.dp, 0.dp)
            .clip(RoundedCornerShape(20.dp)),
        containerColor = colorResource(id = R.color.orange),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile,
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.orange),
                    unselectedIconColor = White,
                    indicatorColor = colorResource(R.color.white)
                ),
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.app_name)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TokoSepatuAppPreview() {
    TokoSepatuTheme {
        TokoSepatuApp()
    }
}