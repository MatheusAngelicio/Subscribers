package subscribers.ui.subscriberlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import br.tecpuc.subscribers.R
import kotlinx.android.synthetic.main.subscriber_list_fragment.*
import subscribers.data.db.AppDatabase
import subscribers.data.db.dao.SubscriberDAO
import subscribers.data.db.entity.SubscriberEntity
import subscribers.repository.DatabaseDataSource
import subscribers.repository.SubscriberRepository
import subscribers.ui.SubscriberViewModel

class SubscriberListFragment : Fragment() {

    private val viewModel: SubscriberListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO =
                    AppDatabase.getInstance(requireContext()).subscriberDAO

                val repository: SubscriberRepository = DatabaseDataSource(subscriberDAO)
                return SubscriberListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.subscriber_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oberveViewModel()


    }

    private fun oberveViewModel() {
        viewModel.allSubscribersEvent.observe(viewLifecycleOwner) { allSubscribers ->
            val subscriberListAdapter = SubscriberListAdapter(allSubscribers)

            recycler_subscribers.run {
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        }

    }


}