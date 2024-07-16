package com.arkteya.vkneewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {   //ищет стартовое состяние графа, даже если является сам граф (для вложенной навигации)
                saveState =
                    true                                    // но у удаленных экранов будет сохранено состояние
            }
            launchSingleTop =
                true                            //настройки экрана, на который перешли - сохранение только 1 копии экрана
            restoreState =
                true                               //восстановление состояния (если экран был удален)
        }
    }


    fun navigateToComments() {
        navHostController.navigate(Screen.Comments.route)
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}