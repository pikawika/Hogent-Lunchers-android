package hogent.be.lunchers.fragments

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_settings.view.*


/**
 * De [SettingsFragment] vooorziet opties voor de gebruiker zijnde zijn blacklist, standaard filtermethode en standaard startscherm.
 */
class SettingsFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over het account
     */
    private lateinit var accountViewModel: AccountViewModel

    /**
     * [LunchViewModel] met de data over de lunches en filters
     */
    private lateinit var lunchViewModel: LunchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)
        lunchViewModel = ViewModelProviders.of(requireActivity()).get(LunchViewModel::class.java)

        initListeners(rootView)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners(rootView: View) {
        //default filter methode
        rootView.btn_settings_default_filter.setOnClickListener {
            showDefaultFilterSelector()
        }

        //default boot page
        rootView.btn_settings_default_tab.setOnClickListener {
            showBootPageSelector()
        }

        //zwarte lijst
        rootView.btn_settings_blacklist.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, BlacklistFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    /**
     * Toont het keuze menu voor de standaard filter en stelt de gekozen standaard fitler in.
     *
     * De lijst van lunches wordt ook gerefresht aangezien een nieuwe filter geselecteerd is.
     */
    private fun showDefaultFilterSelector(){
        val filters = arrayOf(
            getString(R.string.ab_filter_price_lowest),
            getString(R.string.ab_filter_price_highest),
            getString(R.string.ab_filter_distance),
            getString(R.string.ab_filter_recent)
        )
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.text_settings_select_default_filter))
        builder.setItems(filters) { _, which ->
            when (filters[which]) {
                getString(R.string.ab_filter_price_lowest) -> {
                    accountViewModel.setDefaultFilterMethod(FilterEnum.PRICE_LOWEST)
                    lunchViewModel.setSelectedFilter(FilterEnum.PRICE_LOWEST)
                }
                getString(R.string.ab_filter_price_highest) -> {
                    accountViewModel.setDefaultFilterMethod(FilterEnum.PRICE_HIGHEST)
                    lunchViewModel.setSelectedFilter(FilterEnum.PRICE_HIGHEST)
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

    /**
     * Toont het keuze menu voor de boot page en stelt de gekozen boot page in.
     */
    private fun showBootPageSelector(){
        val pages = arrayOf(
            getString(R.string.text_map_title),
            getString(R.string.text_list_title),
            getString(R.string.text_profile_title),
            getString(R.string.text_order_list_title)
        )
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.text_settings_select_default_boot))
        builder.setItems(pages) { _, which ->
            when (pages[which]) {
                getString(R.string.text_map_title) -> accountViewModel.setDefaultBootPage(PageEnum.MAP)
                getString(R.string.text_list_title) -> accountViewModel.setDefaultBootPage(PageEnum.LUNCH_LIST)
                getString(R.string.text_profile_title) -> accountViewModel.setDefaultBootPage(PageEnum.PROFILE)
                getString(R.string.text_order_list_title) -> accountViewModel.setDefaultBootPage(PageEnum.ORDERS_LIST)
            }
        }
        builder.show()
    }

    /**
     * Stel de actionbar zijn titel in en enable back knop
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_settings_title))
        GuiUtil.setCanPop(requireActivity() as MainActivity)
    }

    /**
     * Disable backnop
     */
    override fun onPause() {
        super.onPause()
        GuiUtil.removeCanPop(requireActivity() as MainActivity)
    }
}
