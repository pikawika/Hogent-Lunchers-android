package hogent.be.lunchers.adapters

import android.app.AlertDialog
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.models.BlacklistedItem
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.item_blacklist.view.*

class BlacklistAdapter(private val parentActivity: MainActivity, private val blacklistedItems: MutableLiveData<List<BlacklistedItem>>): RecyclerView.Adapter<BlacklistAdapter.ViewHolder>() {

    private var accountViewModel: AccountViewModel = ViewModelProviders.of(parentActivity).get(AccountViewModel::class.java)

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val selectedBlacklistedItem = v.tag as BlacklistedItem

            val builder = AlertDialog.Builder(parentActivity)
            builder.setCancelable(true)
            builder.setTitle("Verwijderen?")
            builder.setMessage("wilt u '${selectedBlacklistedItem.blacklistedItemName}' uit uw zwarte lijst verwijderen?")
            builder.setPositiveButton(
                "ja"
            ) { dialog, which ->
                //op ja geklikt
                accountViewModel.deleteBlacklistedItem(selectedBlacklistedItem.blacklistedItemId)
            }
            builder.setNegativeButton(
                "nee"
            ) { dialog, which -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blacklist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = blacklistedItems.value!![position]
        holder.blacklistedItemTitel.text = item.blacklistedItemName

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = blacklistedItems.value!!.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blacklistedItemTitel: TextView = view.text_item_blacklist
    }
}