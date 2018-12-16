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

class BlacklistAdapter(
    private val parentActivity: MainActivity,
    private val blacklistedItems: MutableLiveData<List<BlacklistedItem>>
) : RecyclerView.Adapter<BlacklistAdapter.ViewHolder>() {

    /**
     * De [AccountViewModel] met informatie over de *aangemelde gebruiker*.
     */
    private var accountViewModel: AccountViewModel =
        ViewModelProviders.of(parentActivity).get(AccountViewModel::class.java)

    /**
     * Een *on click listener* die er voor zorgt dat op het klikken van een item gevraagd wordt of
     * deze verwijderd dient te worden en die actie uitvoert.
     */
    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            //indien op een item geklikt haal uit de tag desbetreffende lunch op en toon wilt u verwijderen
            val selectedBlacklistedItem = v.tag as BlacklistedItem

            val builder = AlertDialog.Builder(parentActivity)
            builder.setCancelable(true)
            builder.setTitle(parentActivity.getString(R.string.question_delete))
            builder.setMessage(
                parentActivity.getString(R.string.question_want_to_delete_following_blacklisted)
                        + ": " + selectedBlacklistedItem.blacklistedItemName + "?"
            )
            builder.setPositiveButton(parentActivity.getString(R.string.text_yes) ) { _, _ ->
                //op ja geklikt -> verwijderen mag
                accountViewModel.deleteBlacklistedItem(selectedBlacklistedItem.blacklistedItemId)
            }
            builder.setNegativeButton(parentActivity.getString(R.string.text_cancel))
            { dialog, _ -> dialog.cancel() }

            //maak dialoog met bovenstaande opties en toon hem
            val dialog = builder.create()
            dialog.show()
        }
    }

    /**
     * *item_blacklist* layout istellen als content van een lijstitem
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blacklist, parent, false)
        return ViewHolder(view)
    }

    /**
     * Vult de viewholder met de nodige data.
     *
     * De viewholder krijgt ook een tag zijnde de bijhorende [BlacklistedItem]
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = blacklistedItems.value!![position]
        holder.blacklistedItemTitleView.text = item.blacklistedItemName

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    /**
     * Methode die recyclerview nodig heeft om te bepalen hoeveel items hij moet renderen.
     *
     * Dit is het aantal items in de meegeven lijst [blacklistedItems].
     */
    override fun getItemCount() = blacklistedItems.value!!.size

    /**
     * Viewholder die:
     * - [blacklistedItemTitleView]
     * zijn bijhorend UI element bijhoud om later op te vullen.
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blacklistedItemTitleView: TextView = view.text_item_blacklist
    }
}