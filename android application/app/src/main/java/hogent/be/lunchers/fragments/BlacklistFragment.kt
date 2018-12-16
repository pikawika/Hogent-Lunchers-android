package hogent.be.lunchers.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.adapters.BlacklistAdapter
import hogent.be.lunchers.models.BlacklistedItem
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_blacklist.*
import kotlinx.android.synthetic.main.fragment_blacklist.view.*


class BlacklistFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    /**
     * [AccountViewModel] met de info over aangemelde user.
     */
    private lateinit var accountViewModel: AccountViewModel

    /**
     * [LunchViewModel] met de info over de lunches.
     */
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * [BlacklistAdapter] die de lijst vult.
     */
    private lateinit var blacklistAdapter: BlacklistAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_blacklist, container, false)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)
        lunchViewModel = ViewModelProviders.of(requireActivity()).get(LunchViewModel::class.java)

        //blacklisteditems van server ophalen
        //dit doen we zodanig altijd de meest up to date versie weergegeven wordt
        accountViewModel.refreshBlacklistedItems()

        //lijst vullen met lunches uit viewmodel.
        //We doen niet direct .value maar behouden het als mutueablelivedata mits we hier op willen op observen
        val blacklistedItems = accountViewModel.getBlacklistedItems()

        //adapter aanmaken die de lijst van blacklistedItems zal weergeven
        blacklistAdapter = BlacklistAdapter(requireActivity() as MainActivity, blacklistedItems)

        //indien de blacklistedItems veranderden moet de adapter opnieuw zijn cards genereren met nieuwe data
        blacklistedItems.observe(this, Observer {
            blacklistAdapter.notifyDataSetChanged()
            //lunches moeten opnieuw opgehaald worden aangezien backend filtering doet
            lunchViewModel.refreshLunches()
        })

        //lijst zijn adapter instellen
        rootView.recycler_blacklist.adapter = blacklistAdapter

        initListeners(rootView)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners(rootView: View) {
        //lijst zijn swipe to refresh listenen
        rootView.swipe_refresh_blacklist.setOnRefreshListener(this)

        //bij het aanklikken van de add fab een popup tonen die om een naam vraagt voor het toe te voegen list item
        rootView.fab_blacklist_add.setOnClickListener {
            MessageUtil.showDialogWithTextInput(
                requireContext(),
                getString(R.string.text_add),
                getString(R.string.text_add_to_blacklist),
                getString(R.string.hint_ingredient_tag),
                addBlacklistItem()
            )
        }
    }

    /**
     * De swipe to refresh zijn refresh methode. Haalt de blacklisted items op.
     */
    override fun onRefresh() {
        retrieveAllBlacklistedItems()
    }

    /**
     * Functie die een nieuwe [BlacklistedItem] toevoegt aan de lijst
     */
    private fun addBlacklistItem() = { name: String -> accountViewModel.addBlacklistedItem(name) }

    /**
     * Haalt alle [BlacklistedItem] op van de backend
     */
    private fun retrieveAllBlacklistedItems() {
        accountViewModel.refreshBlacklistedItems()
        swipe_refresh_blacklist?.isRefreshing = false
    }

    /**
     * Stel de actionbar zijn titel in en enable back knop
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_settings_blacklist))
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