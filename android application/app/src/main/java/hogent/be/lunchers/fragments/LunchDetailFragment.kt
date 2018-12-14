package hogent.be.lunchers.fragments

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.databinding.FragmentLunchDetailBinding
import hogent.be.lunchers.databinding.FragmentProfileBinding
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_lunch_detail.view.*


class LunchDetailFragment : Fragment() {

    /**
     * [LunchViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [FragmentProfileBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: FragmentLunchDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lunch_detail, container, false)

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(activity!!).get(LunchViewModel::class.java)

        val rootView = binding.root
        binding.lunchViewModel = lunchViewModel
        binding.setLifecycleOwner(activity)

        (activity as MainActivity).supportActionBar?.title = lunchViewModel.getSelectedLunch().value?.naam

        initListeners(rootView)

        return rootView
    }

    private fun initListeners(rootView: View){
        //reserveren
        rootView.button_lunch_detail_reserve.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, ReservationFragment())
                .addToBackStack(null)
                .commit()
        }

        //bel
        rootView.button_lunch_detail_call.setOnClickListener{
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(true)
            builder.setTitle("Bellen naar " + lunchViewModel.getSelectedLunch().value?.handelaar?.handelsNaam)
            builder.setMessage("Wil je nu bellen naar " + lunchViewModel.getSelectedLunch().value?.handelaar?.telefoonnummer + "?")
            builder.setPositiveButton("Nu bellen"
            ) { dialog, which ->
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                phoneIntent.data = Uri.parse("tel:"+lunchViewModel.getSelectedLunch().value?.handelaar?.telefoonnummer)
                startActivity(phoneIntent)
            }
            builder.setNegativeButton("Annuleren"
            ) { dialog, which -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
        }

        //locatie clicked
        rootView.text_lunch_detail_location_restaurant.setOnClickListener{
            fragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, MapsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = lunchViewModel.getSelectedLunch().value!!.naam
        MainActivity.setCanpop(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        MainActivity.setCanpop(false)
    }
}
