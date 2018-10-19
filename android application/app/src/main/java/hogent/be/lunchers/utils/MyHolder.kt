package hogent.be.lunchers.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import hogent.be.lunchers.R
import hogent.be.lunchers.model.Lunch

class MyHolder (itemView: View, private val mContext: Context) : RecyclerView.ViewHolder(itemView) {

    private val iv_afbeelding: ImageView
    private val tv_naam: TextView
    private val tv_prijs: TextView
    private val tv_beschrijving: TextView

    init {
        iv_afbeelding = itemView.findViewById<View>(R.id.imageview_list_item_afbeelding) as ImageView
        tv_naam = itemView.findViewById<View>(R.id.textview_list_item_naam) as TextView
        tv_prijs= itemView.findViewById<View>(R.id.textview_list_item_prijs) as TextView
        tv_beschrijving = itemView.findViewById<View>(R.id.textview_list_item_beschrijving) as TextView
    }

    @SuppressLint("SetTextI18n")
    fun index(lunch: Lunch) {
        Glide.with(mContext).load(lunch.afbeeldingen[0]).into(iv_afbeelding)
        tv_naam.text = lunch.naam
        tv_prijs.text = "€ ${lunch.prijs}"
        tv_beschrijving.text = lunch.beschrijving
    }

}