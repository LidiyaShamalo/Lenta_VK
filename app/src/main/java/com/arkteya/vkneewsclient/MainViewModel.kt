package com.arkteya.vkneewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.domain.StatisticItem
import com.arkteya.vkneewsclient.domain.StatisticType
import com.arkteya.vkneewsclient.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10) {
           add(FeedPost(id = it))
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(sourceList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts


    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val oldPosts =
            feedPosts.value?.toMutableList() ?: mutableListOf()      //получаем копию объета Пост
        val oldStatistics =
            feedPost.statistics                                                //получаем статистику поста
                ?: throw IllegalStateException("statistic item")         //получаем объект статистики
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
        _feedPosts.value =
            oldPosts.apply {
                replaceAll {                                //замена старого объекта новым replaceAll - меняет данные, но ничего не возвращает,
                    // apply - вернет коллекцию и т.о. присваивается в   _feedPosts.value
                    if (it.id == newFeedPost.id) {
                        newFeedPost
                    } else {
                        it
                    }
                }
            }
    }
fun delete (feedPost: FeedPost){
    val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
    oldPosts.remove(feedPost)
    _feedPosts.value = oldPosts
}

}