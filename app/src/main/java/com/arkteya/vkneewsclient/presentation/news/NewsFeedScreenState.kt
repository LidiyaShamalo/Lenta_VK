package com.arkteya.vkneewsclient.presentation.news

import com.arkteya.vkneewsclient.domain.FeedPost

sealed class NewsFeedScreenState {                               //состояние экрана

    object Initial: NewsFeedScreenState()                    //дефолтное значение - ничего отрисовываться не будет
    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()

}