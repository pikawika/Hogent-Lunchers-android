package hogent.be.lunchers.fragments

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lennertbontinck.carmeetsandroidapp.enums.FilterEnum
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_settings, container, false)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(activity!!).get(AccountViewModel::class.java)

        setListeners(rootView)

        return rootView
    }

    private fun setListeners(rootView: View) {
        //default filter methode
        rootView.btn_settings_default_filter.setOnClickListener {
            val pages = arrayOf(getString(R.string.ab_filter_prijs_oplopend), getString(R.string.ab_filter_prijs_aflopend), getString(R.string.ab_filter_afstand), getString(R.string.ab_filter_nieuwste))
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.text_select_default_boot))
            builder.setItems(pages) { dialog, which ->
                if (pages[which] == getString(R.string.ab_filter_prijs_oplopend))
                    accountViewModel.setDefaultFilterMethod(FilterEnum.PRICELOWEST)
                if (pages[which] == getString(R.string.ab_filter_prijs_aflopend))
                    accountViewModel.setDefaultFilterMethod(FilterEnum.PRICEHIGHEST)
                if (pages[which] == getString(R.string.ab_filter_afstand))
                    accountViewModel.setDefaultFilterMethod(FilterEnum.DISTANCE)
                if (pages[which] == getString(R.string.ab_filter_nieuwste))
                    accountViewModel.setDefaultFilterMethod(FilterEnum.RECENT)

            }
            builder.show()
        }

        //default boot page
        rootView.btn_settings_default_tab.setOnClickListener {
            val pages = arrayOf(getString(R.string.title_map), getString(R.string.title_list), getString(R.string.title_profile))
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(getString(R.string.text_select_default_boot))
            builder.setItems(pages) { dialog, which ->
                MessageUtil.showToast(pages[which])
            }
            builder.show()
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPause() {
        super.onPause()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
