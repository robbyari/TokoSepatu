package com.robbyari.tokosepatu

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.robbyari.tokosepatu.data.ShoesRepository
import com.robbyari.tokosepatu.ui.ViewModelFactory
import com.robbyari.tokosepatu.ui.components.TopBarHome
import com.robbyari.tokosepatu.ui.navigation.NavigationItem
import com.robbyari.tokosepatu.ui.navigation.Screen
import com.robbyari.tokosepatu.ui.screen.cart.CartScreen
import com.robbyari.tokosepatu.ui.screen.favorite.FavoriteScreen
import com.robbyari.tokosepatu.ui.screen.home.HomeScreen
import com.robbyari.tokosepatu.ui.screen.profile.ProfileScreen
import com.robbyari.tokosepatu.ui.theme.TokoSepatuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokoSepatuApp(
    modifier: Modifier = Modifier,
    viewModel: TokoSepatuViewModel = viewModel(factory = ViewModelFactory(ShoesRepository())),
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        topBar = { TopBarHome() },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { BottomBar(navController) },
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
                CartScreen()
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    navContoller: NavHostController,
    modifier: Modifier = Modifier
    ) {
    BottomAppBar(
        modifier = modifier
            .padding(30.dp, 0.dp, 30.dp, 0.dp)
            .clip(RoundedCornerShape(20.dp)),

    ) {
        val navBackStackEntry by navContoller.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navContoller.navigate(item.screen.route) {
                        popUpTo(navContoller.graph.findStartDestination().id) {
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