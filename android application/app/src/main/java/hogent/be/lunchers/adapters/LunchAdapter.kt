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
import hogent.be.lunchers.constants.BASE_URL_BACKEND
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
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private var lunchViewModel: LunchViewModel

    private val onClickListener: View.OnClickListener

    init {
        lunchViewModel = ViewModelProviders.of(parentActivity).get(LunchViewModel::class.java)
        onClickListener = View.OnClickListener { v ->
            val selectedLunch = v.tag as Lunch
            lunchViewModel.setSelectedLunch(selectedLunch.lunchId)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lunch, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lunches.value!![position]
        Glide.with(parentActivity).load(BASE_URL_BACKEND + item.afbeeldingen[0].pad).into(holder.afbeeldingView)
        holder.naamView.text = item.naam
        holder.beschrijvingView.text = item.beschrijving
        holder.prijsView.text = String.format("€ %.2f", item.prijs)
        holder.restaurant.text = item.handelaar.handelsNaam
        holder.location.text = StringFormattingUtil.locationToString(item.handelaar.locatie)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = lunches.value!!.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val afbeeldingView: ImageView = view.imageview_list_item_afbeelding
        val naamView: TextView = view.text_item_lunch_name
        val beschrijvingView: TextView = view.text_item_lunch_description
        val prijsView: TextView = view.text_item_lunch_price
        val restaurant: TextView = view.text_item_lunch_restaurant
        val location: TextView = view.text_item_lunch_location
    }
}