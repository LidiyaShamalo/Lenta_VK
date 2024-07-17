package com.arkteya.vkneewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.arkteya.vkneewsclient.domain.FeedPost
import com.google.gson.Gson

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
){
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ){
        composable(Screen.NewsFeed.route){
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST){
                    type = FeedPost.NavigationType
                },

            )
        ){
            val feedPost = it.arguments?.getParcelable<FeedPost>(Screen.KEY_FEED_POST) ?: throw RuntimeException ("Args is null")
            commentsScreenContent(feedPost)
        }
    }
}