package com.arkteya.vkneewsclient.ui.theme

import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.domain.PostComment

sealed class HomeScreenState {                               //состояние экрана

    object Initial: HomeScreenState()                    //дефолтное значение - ничего отрисовываться не будет
    data class Posts(val posts: List<FeedPost>) : HomeScreenState()
    // 1 состояние - отображение списка постов - должен содержать коллекцию постов

    data class Comments(val feedPost: FeedPost, val comments: List<PostComment>) : HomeScreenState()
// 2 - отображение списка комментариев (коментарии к какому посту, список комментариев)
}