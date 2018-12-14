package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_thank_you.view.*

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_thank_you, container, false)

        rootView.backButton.setOnClickListener {
            activity!!.bottom_navigation_view.selectedItemId = R.id.action_list
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.reservatie_geplaatst)
        MainActivity.setCanpop(false)
    }
}
