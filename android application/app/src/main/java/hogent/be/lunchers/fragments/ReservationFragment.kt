package hogent.be.lunchers.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.viewmodels.AccountViewModel
import hogent.be.lunchers.viewmodels.ReservationViewModel
import kotlinx.android.synthetic.main.fragment_reservation.*
import kotlinx.android.synthetic.main.fragment_reservation.view.*
import kotlinx.android.synthetic.main.lunch_detail.view.*
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val LUNCH_ID = "the id for placing the reservation"
private const val LUNCH_NAME = "the name for the string that is shown on screen"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReservationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReservationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ReservationFragment : Fragment() {

    private var lunchID: Int? = null
    private var lunchName: String? = null
    private lateinit var reservationViewModel : ReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lunchID = it.getInt(LUNCH_ID)
            lunchName = it.getString(LUNCH_NAME)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_reservation, container, false)
        reservationViewModel = ViewModelProviders.of(activity!!).get(ReservationViewModel::class.java)


        rootView.reserveren_title.text = lunchName +" reserveren"

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        var month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val hh = c.get(Calendar.HOUR_OF_DAY)
        val mm = c.get(Calendar.MINUTE)

        rootView.datePickerButton.setOnClickListener{


            val dpd = DatePickerDialog(activity,android.R.style.Theme_Holo_Light_Dialog, DatePickerDialog.OnDateSetListener{
                datePicker, _year, _month, _day ->
                var _right_month = _month+1
                rootView.datePickerButton.text = "$_day/$_right_month/$_year"
            },year,month,day)

            //datepicker tonen
            dpd.show()
        }

        rootView.timePickerButton.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker, hour, min ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, min)

                rootView.timePickerButton.text = "$hour:$min"
            }
            TimePickerDialog(activity,timeSetListener, hh, mm, true).show()
        }


        rootView.reserveren_button.setOnClickListener{

                val builder = AlertDialog.Builder(activity)
                builder.setCancelable(true)
                builder.setTitle("Bevestigen")
                builder.setMessage("Bevestig dat je ${lunchName} wenst te reserveren.")
                builder.setPositiveButton("Reserveren"
                ) { dialog, which ->
                    //check valid input

                    reservationViewModel.reserveer(lunchID!!, rootView.aantalInput.text.toString().toInt())

                    fragmentManager!!.beginTransaction()
                        .replace(R.id.fragment_container, ThankYouFragment.newInstance(
                            lunchID!!,lunchName!!,
                            "$day/$month/$year",
                            "$hh:$mm"))
                        .addToBackStack(null)
                        .commit()
                }
                builder.setNegativeButton("Annuleren"
                ) { dialog, which -> dialog.cancel() }

                val dialog = builder.create()
                dialog.show()






        }

        rootView.annuleerButton.setOnClickListener{
            fragmentManager!!.popBackStackImmediate()
        }
        return rootView
    }


    companion object {
        @JvmStatic
        fun newInstance(id: Int, name: String) =
                ReservationFragment().apply {
                    arguments = Bundle().apply {
                        putInt(LUNCH_ID, id)
                        putString(LUNCH_NAME, name)
                    }
                }
    }
}
