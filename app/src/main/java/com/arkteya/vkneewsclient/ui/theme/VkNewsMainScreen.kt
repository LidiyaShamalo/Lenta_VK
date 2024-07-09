package com.arkteya.vkneewsclient.ui.theme

import android.util.Log
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arkteya.vkneewsclient.MainViewModel
import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.navigation.AppNavGraph

//@Composable
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    model: FeedPost,
) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.outline,
                ) {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRoute == item.screen.route,
                        onClick = { navHostController.navigate(item.screen.route)},
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },

                        //selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        //unselectedContentColor = MaterialTheme.colorScheme.onSecondary

                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = {
                HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
            },
            favoriteScreenContent = { TextCounter(name = "Favorite") },
            profileScreenContent = { TextCounter(name = "Profile") }
        )
    }
}

@Composable
private fun TextCounter(name: String) {
    var count by remember {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count",
        color = Color.Black
    )
}




