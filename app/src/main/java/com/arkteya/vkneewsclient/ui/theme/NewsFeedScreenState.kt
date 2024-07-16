package com.arkteya.vkneewsclient.ui.theme

import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.domain.PostComment

sealed class NewsFeedScreenState {                               //состояние экрана

    object Initial: NewsFeedScreenState()                    //дефолтное значение - ничего отрисовываться не будет
    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()

}