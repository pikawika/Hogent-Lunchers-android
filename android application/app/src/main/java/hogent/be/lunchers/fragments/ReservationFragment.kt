package hogent.be.lunchers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import kotlinx.android.synthetic.main.fragment_reservation.*
import kotlinx.android.synthetic.main.fragment_reservation.view.*
import kotlinx.android.synthetic.main.lunch_detail.view.*


// TODO: Rename parameter arguments, choose names that match
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
    // TODO: Rename and change types of parameters
    private var lunchID: Int? = null
    private var lunchName: String? = null

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

        rootView.nameofTextview.text = lunchName + " Reserveren"
        rootView.reserveren_button.setOnClickListener{

            //checken op valid input
            //popup maken met "bent u zeker dat u wil reserveren, als u reserveerd gaat u akkoord met onze algemene voorwaarden" enz
            fragmentManager!!.beginTransaction()
                    .replace(R.id.fragment_container, ThankYouFragment.newInstance(
                            lunchID!!,lunchName!!,
                            rootView.datumInput.text.toString(),
                            rootView.uurInput.text.toString()))
                    .addToBackStack(null)
                    .commit()

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
