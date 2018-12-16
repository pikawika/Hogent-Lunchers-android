package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.utils.GuiUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_thanks.view.*

/**
 * Een [Fragment] voor het weergeven van een bedankt boodschap na het plaatsen van een reservatie via [ReservationFragment]
 */
class ThankYouFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_thanks, container, false)

        initListeners(rootView)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners(rootView: View) {
        rootView.btn_thanks_back.setOnClickListener {
            requireActivity().bottom_navigation_mainactivity.selectedItemId = R.id.action_list
        }

        rootView.btn_thanks_reservations.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, OrderListFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    /**
     * Stel de actionbar zijn titel in
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_reservation_placed))
    }
}
