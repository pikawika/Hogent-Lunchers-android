package hogent.be.lunchers.fragments

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.databinding.FragmentProfileBinding
import hogent.be.lunchers.databinding.PartialLunchCardBinding
import hogent.be.lunchers.viewmodels.LunchViewModel

class PartialLunchCardFragment : Fragment() {

    /**
     * [LunchViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [FragmentProfileBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: PartialLunchCardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.partial_lunch_card, container, false)

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(activity!!).get(LunchViewModel::class.java)

        val rootView = binding.root
        binding.lunchViewModel = lunchViewModel
        binding.setLifecycleOwner(activity)

        setListeners(rootView)

        return rootView
    }

    private fun setListeners(rootView: View) {
        rootView.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, LunchDetailFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}