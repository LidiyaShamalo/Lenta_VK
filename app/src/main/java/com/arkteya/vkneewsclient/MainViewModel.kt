package com.arkteya.vkneewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.domain.PostComment
import com.arkteya.vkneewsclient.domain.StatisticItem
import com.arkteya.vkneewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val comments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(PostComment(id = it))
        }
    }

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }


    private val initialState = HomeScreenState.Posts(posts = sourceList)  // эта строка

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)  // плюс эта
    val screenState: LiveData<HomeScreenState> =
        _screenState    //плюс эта - создают единый стейт экрана, на который можно подписываться

    private var savedState: HomeScreenState? = initialState

    fun showComments(
        feedPost: FeedPost
    ) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost = feedPost, comments = comments)
    }

    fun closeComments() {
        _screenState.value = savedState
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState =
            screenState.value                         //получение текущего состояния экрана
        if (currentState !is HomeScreenState.Posts) return            //если текущее состояние не равно экрану с постами, ничего не делать

        val oldPosts = currentState.posts.toMutableList()           //получаем копию объета Пост
        val oldStatistics = feedPost.statistics                     //получаем объект статистики
        val newStatistics = oldStatistics.toMutableList().apply { //делает его изменяемым
            replaceAll { oldItem ->                                      //применяем метод
                if (oldItem.type == item.type) {                    // если тип клика совпадает с элементом объекта
                    oldItem.copy(count = oldItem.count + 1)     //все поля у элемента сохранить, кроме count и прибавить к нему единицу
                } else {
                    oldItem                                   //тут понятно
                }
            }
        }
        val newFeedPost =
            feedPost.copy(statistics = newStatistics)    //обновляем пост с новой статистикой
        val newPosts = oldPosts.apply {
            replaceAll {                                //замена старого объекта новым replaceAll - меняет данные, но ничего не возвращает,
                // apply - вернет коллекцию и т.о. присваивается в   _feedPosts.value
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value =
            HomeScreenState.Posts(posts = newPosts)   //клаадет новую коллекцию постов

    }

    fun delete(feedPost: FeedPost) {
        val currentState =
            screenState.value                         //получение текущего состояния экрана
        if (currentState !is HomeScreenState.Posts) return            //если текущее состояние не равно экрану с постами, ничего не делать

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(posts = oldPosts)
    }

}