package hogent.be.lunchers.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.databinding.FragmentProfileBinding
import hogent.be.lunchers.databinding.FragmentReservationBinding
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.LunchViewModel
import hogent.be.lunchers.viewmodels.ReservationViewModel
import kotlinx.android.synthetic.main.fragment_reservation.*
import kotlinx.android.synthetic.main.fragment_reservation.view.*
import android.arch.lifecycle.Observer
import hogent.be.lunchers.activities.MainActivity
import java.util.Calendar

@Suppress("DEPRECATION")
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

    private lateinit var reservationViewModel: ReservationViewModel

    /**
     * [LunchViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [FragmentProfileBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: FragmentReservationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation, container, false)

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(activity!!).get(LunchViewModel::class.java)
        reservationViewModel = ViewModelProviders.of(activity!!).get(ReservationViewModel::class.java)

        reservationViewModel.clear()
        reservationViewModel.setSelectedLunch(lunchViewModel.getSelectedLunch().value!!)

        val gereserveerd = reservationViewModel.getGereserveerd()

        gereserveerd.observe(this, Observer {
            if (gereserveerd.value == true) {
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, ThankYouFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })

        val rootView = binding.root
        binding.reservationViewModel = reservationViewModel
        binding.setLifecycleOwner(activity)

        initListeners(rootView)

        return rootView
    }

    private fun initListeners(rootView: View) {
        rootView.btn_reservation_confirm.setOnClickListener {
            reserveren()
        }

        rootView.btn_reservation_cancel.setOnClickListener {
            activity!!.supportFragmentManager.popBackStackImmediate()
        }

        rootView.btn_reservation_pick_date.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        reservationViewModel.year = year
                        reservationViewModel.month = monthOfYear + 1
                        reservationViewModel.day = dayOfMonth
                        rootView.btn_reservation_pick_date.text = "$day/$month/$year"
                    }, year, month, day)
            dpd.show()
        }

        rootView.btn_reservation_pick_time.setOnClickListener {
            val c = Calendar.getInstance()

            val hh = c.get(Calendar.HOUR_OF_DAY)
            val mm = c.get(Calendar.MINUTE)
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->
                reservationViewModel.hour = hour
                reservationViewModel.minute = min

                rootView.btn_reservation_pick_time.text = "$hour:$min"
            }
            TimePickerDialog(activity, timeSetListener, hh, mm, true).show()
        }
    }

    private fun reserveren() {
        if (text_reservation_amount.text.toString().toIntOrNull() != null)
            reservationViewModel.amount = text_reservation_amount.text.toString().toInt()

        reservationViewModel.message = text_reservation_message.text.toString()

        if (reservationViewModel.valid()) {
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(true)
            builder.setTitle("Bevestiging reservatie")
            builder.setMessage("Bevestig dat je " +
                    "${reservationViewModel.amount} keer " +
                    "${reservationViewModel.getSelectedLunch().value!!.naam} wenst te reserveren op " +
                    "${reservationViewModel.day}/${reservationViewModel.month}/${reservationViewModel.year} om " +
                    "${reservationViewModel.hour}:${reservationViewModel.minute}.")

            builder.setPositiveButton("Reserveren") { _, _ -> reservationViewModel.reserveer()}
            builder.setNegativeButton("Annuleren") { dialog, _ -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
        }
        else { MessageUtil.showToast("Gelieve het hele formulier in te vullen.") }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.reservatie_titel)
        MainActivity.setCanpop(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        MainActivity.setCanpop(false)
    }

}
