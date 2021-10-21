package subscribers.ui.subscriberlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import subscribers.data.db.entity.SubscriberEntity
import subscribers.repository.SubscriberRepository

class SubscriberListViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    private val _allSubscribersEvent = MutableLiveData<List<SubscriberEntity>>()
    val allSubscribersEvent: LiveData<List<SubscriberEntity>>
        get() = _allSubscribersEvent

    fun getSubscribers() = viewModelScope.launch {
        _allSubscribersEvent.postValue(repository.getAllSubscribers()
            .sortedBy { it.name })
    }

}