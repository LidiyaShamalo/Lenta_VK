package com.arkteya.vkneewsclient.ui.theme

import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.domain.PostComment

sealed class CommentsScreenState {                               //состояние экрана

    object Initial: CommentsScreenState()                    //дефолтное значение - ничего отрисовываться не будет

  data class Comments(
      val feedPost: FeedPost,
      val comments: List<PostComment>
  ) : CommentsScreenState()     // 2 - отображение списка комментариев (коментарии к какому посту, список комментариев)
}