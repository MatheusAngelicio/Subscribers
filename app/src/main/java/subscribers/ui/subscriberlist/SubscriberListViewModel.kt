package subscribers.ui.subscriberlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.tecpuc.subscribers.R
import kotlinx.coroutines.launch
import subscribers.data.db.entity.SubscriberEntity
import subscribers.repository.SubscriberRepository
import subscribers.ui.SubscriberViewModel

class SubscriberListViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    private val _allSubscribersEvent = MutableLiveData<List<SubscriberEntity>>()
    val allSubscribersEvent: LiveData<List<SubscriberEntity>>
        get() = _allSubscribersEvent

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun getSubscribers() = viewModelScope.launch {
        runCatching {
            _allSubscribersEvent.postValue(repository.getAllSubscribers()
                .sortedBy { it.name })
        }
    }

    fun removeAllSubscribers() = viewModelScope.launch {
        try {
            repository.deleteAllSubscribers()
            _messageEventData.value = R.string.subscriber_removed_successfully
        } catch (e: Exception) {
            _messageEventData.value = R.string.subscriber_removed_error
            Log.e(TAG, e.toString())
        }
    }

    companion object {
        private val TAG = SubscriberListViewModel::class.java.simpleName
    }

}