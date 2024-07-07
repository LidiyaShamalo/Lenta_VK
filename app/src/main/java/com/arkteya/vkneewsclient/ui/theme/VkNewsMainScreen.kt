package com.arkteya.vkneewsclient.ui.theme

import android.util.Log
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkteya.vkneewsclient.MainViewModel
import com.arkteya.vkneewsclient.domain.FeedPost
import kotlinx.coroutines.launch

//@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel) {


    Scaffold(

        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.outline,

                ) {
                Log.d("COMPOSE_TEST", "Bottom_Navigation")

                val selectedItemPosition = remember {
                    mutableStateOf(0)
                }

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value == index },
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
    ) {
        val feedPost =
            viewModel.feedPost.observeAsState(FeedPost())           //обновление состони статистики

        PostCard(
            modifier = Modifier.padding(8.dp),
            feedPost = feedPost.value,
            onViewClickListener = viewModel::updateCount,
            onLikeClickListener = {
                viewModel.updateCount(it)
            },
            onShareClickListener = {
                viewModel.updateCount(it)
            },
            onCommentClickListener = {
                viewModel.updateCount(it)
            },

            )
    }
}



