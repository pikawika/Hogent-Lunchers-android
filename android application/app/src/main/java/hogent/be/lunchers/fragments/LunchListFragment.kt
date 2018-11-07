package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.adapters.LunchAdapter
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.network.NetworkApi
import hogent.be.lunchers.utils.Utils
import kotlinx.android.synthetic.main.lunch_list.*
import kotlinx.android.synthetic.main.lunch_list.view.*
import retrofit2.Call
import retrofit2.Callback

class LunchListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var twoPane: Boolean = false
    private lateinit var lunches: MutableList<Lunch>
    private lateinit var lunchAdapter: LunchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.lunch_list, container, false)

        if (rootView.lunch_detail_container != null) {
            twoPane = true
        }

        lunches = mutableListOf()

        lunchAdapter = LunchAdapter(this.requireActivity() as MainActivity, lunches, twoPane)

        rootView.lunch_list.adapter = lunchAdapter

        rootView.swipe_refresh_layout.setOnRefreshListener(this)

        rootView.swipe_refresh_layout.post {
            rootView.swipe_refresh_layout.isRefreshing = true

            retrieveAllLunches()
        }

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
        swipe_refresh_layout.isRefreshing = true

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
                    swipe_refresh_layout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<List<Lunch>>, t: Throwable) {
                Utils.makeToast(context!!, getString(R.string.network_error))
                swipe_refresh_layout.isRefreshing = false
                Log.e("NOPE", "DAT IS ER NAAAAAST ${t.message}")
            }
        })
    }
}