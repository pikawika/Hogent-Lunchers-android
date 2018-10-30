package hogent.be.lunchers.adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import hogent.be.lunchers.LunchDetailActivity
import hogent.be.lunchers.LunchDetailFragment
import hogent.be.lunchers.LunchListActivity
import hogent.be.lunchers.R
import hogent.be.lunchers.models.Lunch
import kotlinx.android.synthetic.main.lunch_list_content.view.*

class SimpleItemRecyclerViewAdapter(private val parentActivity: LunchListActivity, private val values: List<Lunch>, private val twoPane: Boolean) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Lunch
            if (twoPane) {
                val fragment = LunchDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(LunchDetailFragment.ARG_ITEM_ID, item)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.lunch_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, LunchDetailActivity::class.java).apply {
                    putExtra(LunchDetailFragment.ARG_ITEM_ID, item)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lunch_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Glide.with(parentActivity).load(item.afbeeldingen[0]).into(holder.afbeeldingView)
        holder.naamView.text = item.naam
        holder.prijsView.text = String.format("€ %.2f", item.prijs)
        holder.beschrijvingView.text = item.beschrijving

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val afbeeldingView: ImageView = view.imageview_list_item_afbeelding
        val naamView: TextView = view.textview_list_item_naam
        val prijsView: TextView = view.textview_list_item_prijs
        val beschrijvingView: TextView = view.textview_list_item_beschrijving
    }
}