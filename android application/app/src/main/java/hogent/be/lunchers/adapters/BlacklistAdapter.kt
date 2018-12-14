package hogent.be.lunchers.adapters

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.constants.BASE_URL_BACKEND
import hogent.be.lunchers.models.BlacklistedItem
import hogent.be.lunchers.utils.OrderUtil.convertIntToStatus
import hogent.be.lunchers.utils.OrderUtil.formatDate
import kotlinx.android.synthetic.main.item_blacklist.view.*
import kotlinx.android.synthetic.main.order_list_content.view.*

class BlacklistAdapter(private val parentActivity: MainActivity, private val blacklistedItems: MutableLiveData<List<BlacklistedItem>>): RecyclerView.Adapter<BlacklistAdapter.ViewHolder>() {

    init {
        //listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blacklist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = blacklistedItems.value!![position]
        holder.blacklistedItemTitel.text = item.allergyNaam
    }

    override fun getItemCount() = blacklistedItems.value!!.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blacklistedItemTitel: TextView = view.txt_item_blacklist
    }
}