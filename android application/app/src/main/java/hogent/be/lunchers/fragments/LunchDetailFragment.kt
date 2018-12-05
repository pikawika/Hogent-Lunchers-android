package hogent.be.lunchers.fragments

import android.Manifest
import android.app.AlertDialog
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
import android.content.DialogInterface
import android.util.Log
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat


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
            rootView.textview_lunch_detail_restaurant.text = lunch!!.handelaar.handelsNaam

            val locatie = lunch!!.handelaar.locatie

            rootView.textview_lunch_location_restaurant.text = "${locatie.straat} ${locatie.huisnummer}, ${locatie.gemeente}"
        }

        rootView.button_lunch_detail_reserveren.setOnClickListener {
            fragmentManager!!.beginTransaction()
                    .replace(R.id.fragment_container, ReservationFragment.newInstance(lunch!!.lunchId, lunch!!.naam))
                    .addToBackStack(null)
                    .commit()
        }

        rootView.button_lunch_detail_bellen.setOnClickListener{
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(true)
            builder.setTitle("Bellen naar " + lunch?.handelaar?.handelsNaam)
            builder.setMessage("Wil je nu bellen naar " + lunch?.handelaar?.telefoonnummer + "?")
            builder.setPositiveButton("Nu bellen"
            ) { dialog, which ->
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                phoneIntent.data = Uri.parse("tel:"+lunch?.handelaar?.telefoonnummer)
                startActivity(phoneIntent)
            }
            builder.setNegativeButton("Annuleren"
            ) { dialog, which -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
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
        const val BASE_URL: String = "https://www.lunchers.ml/"
    }
}
