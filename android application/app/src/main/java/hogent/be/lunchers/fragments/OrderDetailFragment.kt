package hogent.be.lunchers.fragments


import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.databinding.FragmentOrderDetailBinding
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order_detail.view.*

class OrderDetailFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: FragmentOrderDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)

        orderViewModel = ViewModelProviders.of(activity!!).get(OrderViewModel::class.java)

        val rootView = binding.root
        binding.orderViewModel = orderViewModel
        binding.setLifecycleOwner(activity)

        initListeners(rootView)

        return rootView
    }

    private fun initListeners(rootView: View) {
        //call restaurant
        rootView.button_order_detail_call.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(true)
            builder.setTitle("Bellen naar " + orderViewModel.selectedOrder.value!!.lunch.merchant.companyName)
            builder.setMessage("Wil je nu bellen naar " + orderViewModel.selectedOrder.value!!.lunch.merchant.phoneNumber + "?")
            builder.setPositiveButton(
                "Nu bellen"
            ) { dialog, which ->
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                phoneIntent.data =
                        Uri.parse("tel:" + orderViewModel.selectedOrder.value!!.lunch.merchant.phoneNumber)
                startActivity(phoneIntent)
            }
            builder.setNegativeButton(
                "Annuleren"
            ) { dialog, which -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
        }

        //navigate to restaurant
        rootView.button_order_detail_navigation.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW)
            mapIntent.data = Uri.parse(
                "geo:" + orderViewModel.selectedOrder.value!!.lunch.merchant.location.latitude + "," +
                        orderViewModel.selectedOrder.value!!.lunch.merchant.location.longitude + "?q=" +
                        orderViewModel.selectedOrder.value!!.lunch.merchant.location.street + "+" +
                        orderViewModel.selectedOrder.value!!.lunch.merchant.location.houseNumber + "+" +
                        orderViewModel.selectedOrder.value!!.lunch.merchant.location.postalCode + "+" +
                        orderViewModel.selectedOrder.value!!.lunch.merchant.location.city
            )
            val packageManager = activity!!.packageManager
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                MessageUtil.showToast(getString(R.string.error_no_navigation_app))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = orderViewModel.selectedOrder.value!!.lunch.name
        MainActivity.setCanpop(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        MainActivity.setCanpop(false)
    }

}
