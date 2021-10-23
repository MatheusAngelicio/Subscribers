package subscribers.ui.subscriberlist

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.tecpuc.subscribers.R
import br.tecpuc.subscribers.databinding.SubscriberListFragmentBinding
import subscribers.data.db.AppDatabase
import subscribers.data.db.dao.SubscriberDAO
import subscribers.extensions.navigateWithAnimations
import subscribers.repository.DatabaseDataSource
import subscribers.repository.SubscriberRepository
import subscribers.ui.subscriberlist.adapter.SubscriberListAdapter

class SubscriberListFragment : Fragment() {

    private lateinit var binding: SubscriberListFragmentBinding

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SubscriberListFragmentBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oberveViewModel()
        configureViewListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_advanced, menu)

        val deleteMenu = menu.findItem(R.id.action_delete)

        initMenus(deleteMenu)


        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initMenus(deleteMenu: MenuItem) {
        deleteMenu.setOnMenuItemClickListener {
            showDeleteAllSubscriberConfirmation(requireContext())
            true
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubscribers()
    }

    private fun oberveViewModel() {
        viewModel.allSubscribersEvent.observe(viewLifecycleOwner) { allSubscribers ->
            val subscriberListAdapter = SubscriberListAdapter(allSubscribers).apply {
                onItemClick = { subscriber ->
                    val directions = SubscriberListFragmentDirections
                        .actionSubscriberListFragmentToSubscriberFragment(subscriber)
                    findNavController().navigateWithAnimations(directions)
                }
            }

            binding.recyclerSubscribers.run {
                setHasFixedSize(true)
                adapter = subscriberListAdapter
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Toast.makeText(requireContext(), stringResId, Toast.LENGTH_LONG).show()
        }
    }

    private fun configureViewListeners() {
        binding.fabAddSubscriber.setOnClickListener {
            findNavController().navigateWithAnimations(
                R.id.action_subscriberListFragment_to_subscriberFragment
            )
        }
    }

    private fun showDeleteAllSubscriberConfirmation(context: Context) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(R.string.remove_all_subscribers)
            .setMessage(R.string.text_remove_all_subscribers)
            .setPositiveButton("Delete") { _, _ ->
                viewModel.removeAllSubscribers()
                viewModel.getSubscribers()

            }.setNegativeButton("Cancel") { _, _ -> }

        builder.create().show()

    }

}