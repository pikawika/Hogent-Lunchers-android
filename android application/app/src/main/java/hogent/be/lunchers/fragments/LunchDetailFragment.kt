package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.adapters.LunchAdapter.Companion.BASE_URL
import hogent.be.lunchers.models.Lunch
import kotlinx.android.synthetic.main.lunch_detail.view.*

class LunchDetailFragment : Fragment() {

    private var lunch: Lunch? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lunch = arguments?.getParcelable(ARG_ITEM_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.lunch_detail, container, false)

        (activity as MainActivity).supportActionBar?.title = lunch?.naam

        if (lunch != null) {
            Glide.with(this).load(BASE_URL + lunch!!.afbeeldingen[0].pad).into(rootView.imageview_lunch_detail_afbeelding)
            rootView.textview_lunch_detail_naam.text = lunch!!.naam
            rootView.textview_lunch_detail_prijs.text = String.format("€ %.2f", lunch!!.prijs)
            rootView.textview_lunch_detail_beschrijving.text = lunch!!.beschrijving
        }

        rootView.button_lunch_detail_reserveren.setOnClickListener {
            Toast.makeText(this.context, "Reserveren is momenteel nog niet mogelijk.", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPause() {
        super.onPause()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    companion object {
        const val ARG_ITEM_ID = "lunchItem"
        const val BASE_URL: String = "http://lunchers.ml/"
    }
}
