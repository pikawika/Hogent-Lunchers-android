package hogent.be.lunchers.fragments

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.viewmodels.AccountViewModel
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_settings.view.*


/**
 * De [SettingsFragment] vooorziet opties voor de gebruiker zijnde zijn blacklist en default boot scherm.
 *
 */
class SettingsFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel: AccountViewModel

    /**
     * [LunchViewModel] met de data over de lunches en filters
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var lunchViewModel: LunchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_settings, container, false)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(activity!!).get(AccountViewModel::class.java)
        lunchViewModel = ViewModelProviders.of(activity!!).get(LunchViewModel::class.java)

        setListeners(rootView)

        return rootView
    }

    private fun setListeners(rootView: View) {
        //default filter methode
        rootView.btn_settings_default_filter.setOnClickListener {
            val filters = arrayOf(getString(R.string.ab_filter_price_az), getString(R.string.ab_filter_price_za), getString(R.string.ab_filter_distance), getString(R.string.ab_filter_recent))
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.text_settings_select_default_filter))
            builder.setItems(filters) { dialog, which ->
                when (filters[which]) {
                    getString(R.string.ab_filter_price_az) -> {
                        accountViewModel.setDefaultFilterMethod(FilterEnum.PRICELOWEST)
                        lunchViewModel.setSelectedFilter(FilterEnum.PRICELOWEST)
                    }
                    getString(R.string.ab_filter_price_za) -> {
                        accountViewModel.setDefaultFilterMethod(FilterEnum.PRICEHIGHEST)
                        lunchViewModel.setSelectedFilter(FilterEnum.PRICEHIGHEST)
                    }
                    getString(R.string.ab_filter_distance) -> {
                        accountViewModel.setDefaultFilterMethod(FilterEnum.DISTANCE)
                        lunchViewModel.setSelectedFilter(FilterEnum.DISTANCE)
                    }
                    getString(R.string.ab_filter_recent) -> {
                        accountViewModel.setDefaultFilterMethod(FilterEnum.RECENT)
                        lunchViewModel.setSelectedFilter(FilterEnum.RECENT)
                    }
                }

            }
            builder.show()
        }

        //default boot page
        rootView.btn_settings_default_tab.setOnClickListener {
            val pages = arrayOf(getString(R.string.text_map_title), getString(R.string.text_list_title), getString(R.string.text_profile_title), getString(R.string.text_order_list_title))
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.text_settings_select_default_boot))
            builder.setItems(pages) { dialog, which ->
                when (pages[which]) {
                    getString(R.string.text_map_title) -> accountViewModel.setDefaultBootPage(PageEnum.MAP)
                    getString(R.string.text_list_title) -> accountViewModel.setDefaultBootPage(PageEnum.LUNCHLIST)
                    getString(R.string.text_profile_title) -> accountViewModel.setDefaultBootPage(PageEnum.PROFILE)
                    getString(R.string.text_order_list_title) -> accountViewModel.setDefaultBootPage(PageEnum.ORDERSLIST)
                }
            }
            builder.show()
        }

        //default boot page
        rootView.btn_settings_blacklist.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, BlacklistFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.text_settings_title)
        MainActivity.setCanpop(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        MainActivity.setCanpop(false)
    }
}
