package com.arkteya.vkneewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkteya.vkneewsclient.domain.FeedPost
import com.arkteya.vkneewsclient.domain.StatisticItem
import java.util.Collections.replaceAll

class MainViewModel: ViewModel() {

    private  val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> = _feedPost

    fun updateCount(item: StatisticItem){
        val oldStatistics = feedPost.value?.statistics ?: throw IllegalStateException("statistic item")         //получаем объект статистики
        val newStatistics = oldStatistics.toMutableList().apply { //делает его изменяемым
            replaceAll { oldItem ->                                      //применяем метод
                if (oldItem.type == item.type) {                    // если тип клика совпадает с элементом объекта
                    oldItem.copy(count = oldItem.count + 1)     //все поля у элемента сохранить, кроме count и прибавить к нему единицу
                } else {
                    oldItem                                   //тут понятно
                }
            }
        }
        _feedPost.value =
            feedPost.value?.copy(statistics = newStatistics)         //теперь меняет весь объект с обновленными данными
    }
}