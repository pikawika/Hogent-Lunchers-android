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
import hogent.be.lunchers.adapters.LunchAdapter
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_lunch_list.*
import kotlinx.android.synthetic.main.fragment_lunch_list.view.*
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.partial_search_filter.view.*


class LunchListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var twoPane: Boolean = false

    /**
     * [LunchViewModel] met de data van alle lunches
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var lunchViewModel : LunchViewModel

    private lateinit var lunchAdapter: LunchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_lunch_list, container, false)

        if (rootView.lunch_detail_container != null) {
            twoPane = true
        }

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(requireActivity()).get(LunchViewModel::class.java)

        //lijst vullen met lunches uit viewmodel.
        //We doen niet direct .value maar behouden het als mutueablelivedata mits we hier op willen op observen
        val lunches = lunchViewModel.getFilteredLunches()


        //adapter aanmaken
        lunchAdapter = LunchAdapter(this.requireActivity() as MainActivity, lunches, twoPane)

        //indien de meetinglijst veranderd moet de adapter opnieuw zijn cards genereren met nieuwe data
        lunches.observe(this, Observer {
            lunchAdapter.notifyDataSetChanged()
        })

        rootView.lunch_list_searchandfilter.txt_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                lunchViewModel.search(s.toString())
            }
        })

        rootView.lunch_list.adapter = lunchAdapter

        rootView.swipe_refresh_layout.setOnRefreshListener(this)

        activity!!.toolbar.menu.findItem(R.id.ab_filter_nieuwste)?.isVisible = true
        activity!!.toolbar.menu.findItem(R.id.ab_filter_afstand)?.isVisible = true
        activity!!.toolbar.menu.findItem(R.id.ab_filter_prijs_aflopend)?.isVisible = true
        activity!!.toolbar.menu.findItem(R.id.ab_filter_prijs_oplopend)?.isVisible = true

        return rootView
    }



    override fun onRefresh() {
        lunch_list_searchandfilter.txt_search.setText("")
        retrieveAllLunches()
        lunchViewModel.setSelectedLunch(0)
    }

    private fun retrieveAllLunches() {
        lunchViewModel.refreshLunches()
        swipe_refresh_layout?.isRefreshing = false
    }

    //opties menu instellen
    @Override
    override fun onStart() {
        activity!!.toolbar.menu.findItem(R.id.ab_filter_nieuwste)?.isVisible = true
        activity!!.toolbar.menu.findItem(R.id.ab_filter_afstand)?.isVisible = true
        activity!!.toolbar.menu.findItem(R.id.ab_filter_prijs_aflopend)?.isVisible = true
        activity!!.toolbar.menu.findItem(R.id.ab_filter_prijs_oplopend)?.isVisible = true
        super.onStart()
    }

    //opties menu verwijderen
    @Override
    override fun onPause() {
        activity!!.toolbar.menu.findItem(R.id.ab_filter_nieuwste)?.isVisible = false
        activity!!.toolbar.menu.findItem(R.id.ab_filter_afstand)?.isVisible = false
        activity!!.toolbar.menu.findItem(R.id.ab_filter_prijs_aflopend)?.isVisible = false
        activity!!.toolbar.menu.findItem(R.id.ab_filter_prijs_oplopend)?.isVisible = false
        super.onPause()
    }


}