package hogent.be.lunchers.fragments


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.databinding.FragmentOrderDetailBinding
import hogent.be.lunchers.viewmodels.OrderViewModel

class OrderDetailFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: FragmentOrderDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)

        orderViewModel = ViewModelProviders.of(activity!!).get(OrderViewModel::class.java)

        val rootView = binding.root
        binding.orderViewModel = orderViewModel;
        binding.setLifecycleOwner(activity)

        return rootView
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.title = orderViewModel.selectedOrder.value!!.lunch.naam
    }

}
