package subscribers.ui.subscriberlist

import androidx.lifecycle.ViewModel
import subscribers.repository.SubscriberRepository

class SubscriberListViewModel(
    private val repository: SubscriberRepository
) : ViewModel() {

    val allSubscribersEvent = repository.getAllSubscribers()


}