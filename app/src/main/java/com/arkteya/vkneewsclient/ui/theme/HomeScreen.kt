package com.arkteya.vkneewsclient.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkteya.vkneewsclient.MainViewModel
import com.arkteya.vkneewsclient.domain.FeedPost
import androidx.compose.material.SwipeToDismiss as SwipeToDismiss1


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {

    val screenState =
        viewModel.screenState.observeAsState(HomeScreenState.Initial)           //подписываемся на стейт

    when (val currentState =
        screenState.value) {              //если этот стейт является списком постов, то вызывается функциия FeedPosts
        is HomeScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentState.posts
            )
        }

        is HomeScreenState.Comments -> {                                 //если этот стейт является типом Comments - отобразаем список комментариев
            CommentsScreen(
                feedPost = currentState.feedPost,
                comments = currentState.comments,
                onBackPressed = {
                    viewModel.closeComments()
                })
            BackHandler {
                viewModel.closeComments()
            }
        }

        HomeScreenState.Initial -> {}          //обработка всех значаний - в данном случае ничего не будет происходить
    }


}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)   //расстояние между постами внутри
    ) {
        items(
            items = posts,
            key = { it.id }
        ) { feedPost ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.delete(feedPost)
            }
            SwipeToDismiss1(
                modifier = Modifier.animateItemPlacement(),     //создает анимацию удаления
                state = dismissState,
                background = {},
                directions = setOf(DismissDirection.EndToStart),
                dismissContent = {
                    PostCard(
                        feedPost = feedPost,
                        onViewClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onLikeClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onCommentClickListener = {
                            viewModel.showComments(feedPost)
                        }
                    )
                }
            )
        }
    }
}