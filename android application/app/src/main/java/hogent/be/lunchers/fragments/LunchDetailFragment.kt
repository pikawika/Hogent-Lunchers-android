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
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_lunch_detail.*

/**
 * Een [Fragment] voor het weergeven van de details van een selectedLunch.
 *
 * Gerbuikt model binding voor de [LunchViewModel.selectedLunch] weer te geven.
 */
class LunchDetailFragment : Fragment() {

    /**
     * [LunchViewModel] met de info over de lunches.
     */
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [FragmentLunchDetailBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: FragmentLunchDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lunch_detail, container, false)

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(requireActivity()).get(LunchViewModel::class.java)

        val rootView = binding.root
        binding.lunchViewModel = lunchViewModel
        binding.setLifecycleOwner(activity)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners() {
        //reserveren
        button_lunch_detail_reserve.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container_mainactivity, ReservationFragment())
                .addToBackStack(null)
                .commit()
        }

        //bel
        button_lunch_detail_call.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(true)
            builder.setTitle(getString(R.string.text_call_to) + ": " + lunchViewModel.selectedLunch.value!!.merchant.companyName)
            builder.setMessage(getString(R.string.text_want_to_call_to) + ": " + lunchViewModel.selectedLunch.value!!.merchant.phoneNumber + "?")
            builder.setPositiveButton(
                getString(R.string.text_yes)
            ) { _, _ ->
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                phoneIntent.data =
                        Uri.parse("tel:" + lunchViewModel.selectedLunch.value!!.merchant.phoneNumber)
                startActivity(phoneIntent)
            }
            builder.setNegativeButton(
                getString(R.string.text_no)
            ) { dialog, _ -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
        }

        //toon op kaart
        button_lunch_detail_show_on_map.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container_mainactivity, MapsFragment())
                .addToBackStack(null)
                .commit()
        }

        //navigatie
        button_lunch_detail_navigation.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW)
            mapIntent.data = Uri.parse(
                "geo:" + lunchViewModel.selectedLunch.value!!.merchant.location.latitude + "," +
                        lunchViewModel.selectedLunch.value!!.merchant.location.longitude + "?q=" +
                        lunchViewModel.selectedLunch.value!!.merchant.location.street + "+" +
                        lunchViewModel.selectedLunch.value!!.merchant.location.houseNumber + "+" +
                        lunchViewModel.selectedLunch.value!!.merchant.location.postalCode + "+" +
                        lunchViewModel.selectedLunch.value!!.merchant.location.city
            )
            //kijk of er gps app is op de gsm
            val packageManager = requireActivity().packageManager
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                MessageUtil.showToast(getString(R.string.error_no_navigation_app))
            }
        }
    }

    /**
     * Stop de listeners
     */
    @Suppress("UNUSED_EXPRESSION")
    private fun stopListeners() {
        //reserveren
        button_lunch_detail_reserve.setOnClickListener { null }

        //bel
        button_lunch_detail_call.setOnClickListener { null }

        //toon op kaart
        button_lunch_detail_show_on_map.setOnClickListener { null }

        //navigatie
        button_lunch_detail_navigation.setOnClickListener { null }
    }

    /**
     * Stel de actionbar zijn titel in en enable back knop
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, lunchViewModel.selectedLunch.value!!.name)
        GuiUtil.setCanPop(requireActivity() as MainActivity)
    }

    /**
     * Disable backnop
     */
    override fun onPause() {
        super.onPause()
        GuiUtil.removeCanPop(requireActivity() as MainActivity)
    }

    override fun onStart() {
        super.onStart()

        initListeners()
    }

    override fun onStop() {
        stopListeners()
        super.onStop()
    }
}
