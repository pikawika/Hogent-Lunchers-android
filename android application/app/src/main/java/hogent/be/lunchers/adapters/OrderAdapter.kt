package hogent.be.lunchers.adapters

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.constants.BASE_URL_BACKEND
import hogent.be.lunchers.fragments.OrderDetailFragment
import hogent.be.lunchers.models.Order
import hogent.be.lunchers.utils.OrderUtil.convertIntToStatus
import hogent.be.lunchers.utils.OrderUtil.formatDate
import hogent.be.lunchers.utils.StringFormattingUtil
import hogent.be.lunchers.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.item_order.view.*

class OrderAdapter(private val parentActivity: MainActivity, private val orders: MutableLiveData<List<Order>>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    /**
     * [OrderViewModel] met de data over de orders
     */
    private var orderViewModel: OrderViewModel = ViewModelProviders.of(parentActivity).get(OrderViewModel::class.java)

    /**
     * Een *on click listener* die er voor zorgt dat op het klikken van een [Order]
     * naar de bijhorende [OrderDetailFragment] gegaan wordt.
     */
    private val onClickListener: View.OnClickListener

    init {
        //indien op een order geklikt haal uit de tag desbetreffende lunch op en toon detailpagina
        onClickListener = View.OnClickListener { v ->
            val selectedOrder = v.tag as Order
            orderViewModel.setSelectedOrder(selectedOrder.reservationId)

            parentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, OrderDetailFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    /**
     * *item_order* layout istellen als content van een lijstitem
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return ViewHolder(view)
    }

    /**
     * Vult de viewholder met de nodige data.
     *
     * De viewholder krijgt ook een tag zijnde de bijhorende [Order]
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orders.value!![position]
        Glide.with(parentActivity).load(BASE_URL_BACKEND + item.lunch.images[0].path).into(holder.orderImageView)
        holder.orderLunchMerchantView.text = item.lunch.merchant.companyName
        holder.orderLunchNameView.text = item.lunch.name
        holder.orderStatusView.text = String.format("Status: %s", convertIntToStatus(item.status))
        holder.orderAmountView.text = StringFormattingUtil.amountOfPeopleToString(item.amount)
        holder.orderDateView.text = formatDate(item.date)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    /**
     * Methode die recyclerview nodig heeft om te bepalen hoeveel items hij moet renderen.
     *
     * Dit is het aantal items in de meegeven lijst [orders].
     */
    override fun getItemCount() = orders.value!!.size

    /**
     * Viewholder die:
     * - [orderImageView]
     * - [orderLunchMerchantView]
     * - [orderLunchNameView]
     * - [orderStatusView]
     * - [orderAmountView]
     * - [orderDateView]
     * hun bijhorend UI element bijhoud om later op te vullen.
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderImageView: ImageView = view.img_item_order
        val orderLunchMerchantView: TextView = view.tv_order_list_content_lunch_merchant
        val orderLunchNameView: TextView = view.tv_order_list_content_lunch_name
        val orderStatusView: TextView = view.tv_order_list_content_status
        val orderAmountView: TextView = view.tv_order_list_content_aantal
        val orderDateView: TextView = view.tv_order_list_content_date
    }
}