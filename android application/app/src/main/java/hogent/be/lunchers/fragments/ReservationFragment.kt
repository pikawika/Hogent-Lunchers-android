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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                    .replace(R.id.fragment_container, ThankYouFragment())
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
        rootView.reserveren_submit_button.setOnClickListener {
            reserveren()
        }

        rootView.reserveren_cancel_button.setOnClickListener {
            activity!!.supportFragmentManager.popBackStackImmediate()
        }

        rootView.reserveren_datePicker_button.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd =
                DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    reservationViewModel.year = year
                    reservationViewModel.month = monthOfYear + 1
                    reservationViewModel.day = dayOfMonth
                    rootView.reserveren_datePicker_button.text = "$day/$month/$year"
                }, year, month, day)
            dpd.show()
        }

        rootView.reserveren_timePicker_button.setOnClickListener {
            val c = Calendar.getInstance()

            val hh = c.get(Calendar.HOUR_OF_DAY)
            val mm = c.get(Calendar.MINUTE)
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->
                reservationViewModel.hour = hour
                reservationViewModel.minute = min

                rootView.reserveren_timePicker_button.text = "$hour:$min"
            }
            TimePickerDialog(activity, timeSetListener, hh, mm, true).show()
        }
    }

    private fun reserveren() {
        if (reserveren_aantal_text.text.toString().toIntOrNull() != null)
            reservationViewModel.amount = reserveren_aantal_text.text.toString().toInt()

        if (reservationViewModel.valid()) {
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(true)
            builder.setTitle("Bevestig reservatie")
            builder.setMessage("Bevestig dat je ${reservationViewModel.amount} keer ${reservationViewModel.getSelectedLunch().value!!.naam} wenst te reserveren op ${reservationViewModel.day}/${reservationViewModel.month}/${reservationViewModel.year}.")
            builder.setPositiveButton(
                "Reserveren"
            ) { dialog, which ->
                //op ja geklikt
                reservationViewModel.reserveer()

            }
            builder.setNegativeButton(
                "Annuleren"
            ) { dialog, which -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
        }
        else{
            MessageUtil.showToast("formulier niet volledig of incorrect ingevuld")
        }

    }
}
