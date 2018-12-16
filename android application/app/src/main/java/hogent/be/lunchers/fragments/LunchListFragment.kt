package hogent.be.lunchers.fragments

import android.arch.lifecycle.MutableLiveData
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
import hogent.be.lunchers.adapters.LunchAdapter
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_lunch_list.*
import kotlinx.android.synthetic.main.fragment_lunch_list.view.*
import android.text.Editable
import android.text.TextWatcher
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.utils.GuiUtil
import kotlinx.android.synthetic.main.partial_search.view.*


class LunchListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    /**
     * [Boolean] of huidige omgeving al dan niet twopane is.
     */
    private var twoPane: Boolean = false

    /**
     * [LunchViewModel] met de data van alle lunches.
     */
    private lateinit var lunchViewModel : LunchViewModel

    /**
     * [LunchAdapter] die de lijst vult.
     */
    private lateinit var lunchAdapter: LunchAdapter

    /**
     * De lijst [Lunch] van de backend.
     */
    private lateinit var lunches: MutableLiveData<List<Lunch>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_lunch_list, container, false)

        checkSetTwoPane(rootView)

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(requireActivity()).get(LunchViewModel::class.java)

        //lijst vullen met lunches uit viewmodel.
        //We doen niet direct .value maar behouden het als mutueablelivedata mits we hier op willen op observen
        lunches = lunchViewModel.getFilteredLunches()

        //adapter aanmaken
        lunchAdapter = LunchAdapter(this.requireActivity() as MainActivity, lunches, twoPane)

        rootView.recycler_lunch_list.adapter = lunchAdapter

        rootView.swipe_refresh_lunch_list.setOnRefreshListener(this)

        initListeners(rootView)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners(rootView: View) {
        //indien de lunchlijst veranderd moet de adapter opnieuw zijn cards genereren met nieuwe data
        lunches.observe(this, Observer {
            lunchAdapter.notifyDataSetChanged()
        })

        rootView.searchpartial_lunch_list.txt_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                lunchViewModel.search(s.toString())
            }
        })
    }


    /**
     * Behandeld wanneer er pull to refresh wordt gedaan.
     *
     * Verwijderd zoeken en geselecteerde lunch.
     *
     * Haalt de lunches op van de server.
     */
    override fun onRefresh() {
        searchpartial_lunch_list.txt_search.setText("")
        retrieveAllLunches()
        lunchViewModel.setSelectedLunch(0)
    }

    /**
     * Haalt de lunches op van de server
     */
    private fun retrieveAllLunches() {
        lunchViewModel.refreshLunches()
        swipe_refresh_lunch_list?.isRefreshing = false
    }

    /**
     * Stel de actionbar zijn titel in en toont de filtermethoden.
     */
    override fun onResume() {
        GuiUtil.showFilterMethods(requireActivity() as MainActivity)
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_lunch_list))
        super.onResume()
    }

    /**
     * Verbergt de filtermethoden.
     */
    override fun onPause() {
        GuiUtil.hideFilterMethods(requireActivity() as MainActivity)
        super.onPause()
    }

    /**
     * Kijkt of twopane is en stelt het in
     */
    private fun checkSetTwoPane(rootView: View) {
        twoPane = rootView.fragment_container_lunch_list != null
    }


}