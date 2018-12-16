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
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order_detail.view.*

class OrderDetailFragment : Fragment() {

    /**
     * [orderViewModel] met de info over de orders.
     */
    private lateinit var orderViewModel: OrderViewModel

    /**
     * De [FragmentOrderDetailBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: FragmentOrderDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)

        //viewmodel vullen
        orderViewModel = ViewModelProviders.of(requireActivity()).get(OrderViewModel::class.java)

        val rootView = binding.root

        //databinding
        binding.orderViewModel = orderViewModel
        binding.setLifecycleOwner(activity)

        initListeners(rootView)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners(rootView: View) {
        //call restaurant
        rootView.button_order_detail_call.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(true)
            builder.setTitle(getString(R.string.text_call_to) + ": " + orderViewModel.selectedOrder.value!!.lunch.merchant.companyName)
            builder.setMessage(getString(R.string.text_want_to_call_to) + ": " + orderViewModel.selectedOrder.value!!.lunch.merchant.phoneNumber + "?")
            builder.setPositiveButton(
                getString(R.string.text_yes)
            ) { _, _ ->
                val phoneIntent = Intent(Intent.ACTION_DIAL)
                phoneIntent.data =
                        Uri.parse("tel:" + orderViewModel.selectedOrder.value!!.lunch.merchant.phoneNumber)
                startActivity(phoneIntent)
            }
            builder.setNegativeButton(
                getString(R.string.text_no)
            ) { dialog, _ -> dialog.cancel() }

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

    /**
     * Stel de actionbar zijn titel in en enable back knop
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_reservation))
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
