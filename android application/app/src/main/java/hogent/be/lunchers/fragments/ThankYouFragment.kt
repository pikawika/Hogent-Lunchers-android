package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import kotlinx.android.synthetic.main.fragment_thank_you.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val LunchID = "param1"
private const val LunchName = "param2"
private const val ReservationDate = "param3"
private const val ReservationTime = "param4"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ThankYouFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ThankYouFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ThankYouFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var lunchID: Int? = null
    private var lunchName: String? = null
    private var reservationDate: String ?= null
    private var reservationTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            reservationDate = it.getString(ReservationDate)
            reservationTime = it.getString(ReservationTime)
            lunchID = it.getInt(LunchID)
            lunchName = it.getString(LunchName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_thank_you, container, false)

        rootView.bedanktMessageTextview.text = "Je reservatie voor ${lunchName} op ${reservationDate} om ${reservationTime} werd geplaatst"

        return rootView
    }


    companion object {
        @JvmStatic
        fun newInstance(lunchId: Int, lunchName: String, reservationDate:String,reservationTime:String) =
                ThankYouFragment().apply {
                    arguments = Bundle().apply {
                        putInt(LunchID, lunchId)
                        putString(LunchName, lunchName)
                        putString(ReservationDate, reservationDate)
                        putString(ReservationTime, reservationTime)
                    }
                }
    }
}
