package subscribers.ui.subscriberlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.tecpuc.subscribers.R
import kotlinx.android.synthetic.main.subscriber_item.view.*
import subscribers.data.db.entity.SubscriberEntity

class SubscriberListAdapter(
    private val subscriber: List<SubscriberEntity>
) : RecyclerView.Adapter<SubscriberListAdapter.SubscriberListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.subscriber_item, parent, false)

        return SubscriberListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubscriberListViewHolder, position: Int) {
        holder.bindView(subscriber[position])
    }

    override fun getItemCount() = subscriber.size

    class SubscriberListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(subscriber: SubscriberEntity) {
            itemView.apply {
                text_subscriber_name.text = subscriber.name
                text_subscriber_email.text = subscriber.email
            }
        }
    }
}