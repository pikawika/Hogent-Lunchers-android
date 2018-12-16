package hogent.be.lunchers.adapters

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import hogent.be.lunchers.fragments.LunchDetailFragment
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.constants.BASE_URL_LUNCHERS
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.utils.StringFormattingUtil
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.item_lunch.view.*

class LunchAdapter(
    private val parentActivity: MainActivity,
    private val lunches: MutableLiveData<List<Lunch>>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<LunchAdapter.ViewHolder>() {

    /**
     * [LunchViewModel] met de data over account
     */
    private var lunchViewModel: LunchViewModel = ViewModelProviders.of(parentActivity).get(LunchViewModel::class.java)

    /**
     * Een *on click listener* die er voor zorgt dat op het klikken van een [Lunch]
     * naar de bijhorende [LunchDetailFragment] gegaan wordt.
     */
    private val onClickListener: View.OnClickListener

    init {
        //indien op een lunch geklikt haal uit de tag desbetreffende lunch op en toon detailpagina
        onClickListener = View.OnClickListener { v ->
            val selectedLunch = v.tag as Lunch
            lunchViewModel.setSelectedLunch(selectedLunch.lunchId)

            //indien twopane moet je het in de subcontainer tonen anders in de container van mainactivity
            if (twoPane) {
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_lunch_list, LunchDetailFragment())
                    .commit()
            } else {
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, LunchDetailFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    /**
     * *item_lunch* layout istellen als content van een lijstitem
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lunch, parent, false)
        return ViewHolder(view)
    }

    /**
     * Vult de viewholder met de nodige data.
     *
     * De viewholder krijgt ook een tag zijnde de bijhorende [Lunch]
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lunches.value!![position]
        Glide.with(parentActivity).load(BASE_URL_LUNCHERS + item.images[0].path).into(holder.lunchImageView)
        holder.lunchNameView.text = item.name
        holder.lunchDescriptionView.text = item.description
        holder.lunchPriceView.text = String.format("%.2f", item.price)
        holder.lunchRestaurantView.text = item.merchant.companyName
        holder.lunchLocationView.text = StringFormattingUtil.locationToString(item.merchant.location)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    /**
     * Methode die recyclerview nodig heeft om te bepalen hoeveel items hij moet renderen.
     *
     * Dit is het aantal items in de meegeven lijst [lunches].
     */
    override fun getItemCount() = lunches.value!!.size

    /**
     * Viewholder die:
     * - [lunchImageView]
     * - [lunchNameView]
     * - [lunchDescriptionView]
     * - [lunchPriceView]
     * - [lunchRestaurantView]
     * - [lunchLocationView]
     * hun bijhorend UI element bijhoud om later op te vullen.
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lunchImageView: ImageView = view.img_item_lunch
        val lunchNameView: TextView = view.text_item_lunch_name
        val lunchDescriptionView: TextView = view.text_item_lunch_description
        val lunchPriceView: TextView = view.text_item_lunch_price
        val lunchRestaurantView: TextView = view.text_item_lunch_restaurant
        val lunchLocationView: TextView = view.text_item_lunch_location
    }
}