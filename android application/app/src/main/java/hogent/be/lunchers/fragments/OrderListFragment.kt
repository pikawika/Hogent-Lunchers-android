package hogent.be.lunchers.fragments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.adapters.OrderAdapter
import hogent.be.lunchers.models.Order
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order_list.view.*

class OrderListFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel

    /**
     * De lijst [Order] van de backend.
     */
    private lateinit var orders: MutableLiveData<List<Order>>

    /**
     * [OrderAdapter] die de lijst vult.
     */
    private lateinit var orderAdapter: OrderAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_order_list, container, false)

        //viewmodel vullen en lokale link bijhouden
        orderViewModel = ViewModelProviders.of(requireActivity()).get(OrderViewModel::class.java)
        orders = orderViewModel.reservations

        orderAdapter = OrderAdapter(requireActivity() as MainActivity, orders)

        rootView.recycler_order_list.adapter = orderAdapter

        initListeners()

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners() {
        orderViewModel.roomOrders.observe(requireActivity() as MainActivity, Observer {
            if (orderViewModel.reservations.value!!.isEmpty()) orderViewModel.setReservations(it!!)
        })

        orders.observe(this, Observer { orderAdapter.notifyDataSetChanged() })
    }

    /**
     * Stel de actionbar zijn titel in en enable back knop
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_order_list_title))
        GuiUtil.setCanPop(requireActivity() as MainActivity)
        orderViewModel.resetViewModel()
    }

    /**
     * Disable backnop
     */
    override fun onPause() {
        super.onPause()
        GuiUtil.removeCanPop(requireActivity() as MainActivity)
    }

}
