package com.arkteya.vkneewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(
                id = it,
               // contentText = "Content $it"
            ))
        }
    }


    private val initialState = NewsFeedScreenState.Posts(posts = sourceList)  // эта строка

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)  // плюс эта
    val screenState: LiveData<NewsFeedScreenState> =
        _screenState    //плюс эта - создают единый стейт экрана, на который можно подписываться



    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState =   screenState.value                         //получение текущего состояния экрана
        if (currentState !is NewsFeedScreenState.Posts) return            //если текущее состояние не равно экрану с постами, ничего не делать

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
        val newFeedPost = feedPost.copy(statistics = newStatistics)    //обновляем пост с новой статистикой
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
            NewsFeedScreenState.Posts(posts = newPosts)   //клаадет новую коллекцию постов

    }

    fun delete(feedPost: FeedPost) {
        val currentState =
            screenState.value                         //получение текущего состояния экрана
        if (currentState !is NewsFeedScreenState.Posts) return            //если текущее состояние не равно экрану с постами, ничего не делать

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }

}