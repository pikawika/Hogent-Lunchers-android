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
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.utils.Utils
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.lunch_list.*
import kotlinx.android.synthetic.main.lunch_list.view.*
import retrofit2.Call
import retrofit2.Callback

class LunchListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var twoPane: Boolean = false

    /**
     * [MeetingViewModel] met de data van alle meetings
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var lunchViewModel : LunchViewModel

    private lateinit var lunchAdapter: LunchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.lunch_list, container, false)

        if (rootView.lunch_detail_container != null) {
            twoPane = true
        }

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(requireActivity()).get(LunchViewModel::class.java)

        //lijst vullen met lunches uit viewmodel.
        //We doen niet direct .value maar behouden het als mutueablelivedata mits we hier op willen op observen
        val lunches = lunchViewModel.getLunches()

        //adapter aanmaken
        lunchAdapter = LunchAdapter(this.requireActivity() as MainActivity, lunches, twoPane)

        //indien de meetinglijst veranderd moet de adapter opnieuw zijn cards genereren met nieuwe data
        lunches.observe(this, Observer {
            lunchAdapter.notifyDataSetChanged()
        })

        rootView.lunch_list.adapter = lunchAdapter

        rootView.swipe_refresh_layout.setOnRefreshListener(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveAllLunches()
    }

    override fun onRefresh() {
        retrieveAllLunches()
    }

    private fun retrieveAllLunches() {
        /*swipe_refresh_layout?.isRefreshing = true

        val apiService = NetworkApi.create()
        val call = apiService.getAllLunches()
        call.enqueue(object : Callback<List<Lunch>> {
            override fun onResponse(call: Call<List<Lunch>>, response: retrofit2.Response<List<Lunch>>?) {
                if (response != null) {
                    val list: List<Lunch>? = response.body()
                    if (list != null) {
                        lunches.clear()
                        lunches.addAll(list)
                        lunchAdapter.notifyDataSetChanged()
                    } else {
                        Utils.makeToast(context!!, getString(R.string.network_error))
                    }
                    swipe_refresh_layout?.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<List<Lunch>>, t: Throwable) {
                Utils.makeToast(context!!, getString(R.string.network_error))
                swipe_refresh_layout?.isRefreshing = false
            }
        })*/
    }
}