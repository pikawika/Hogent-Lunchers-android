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
     * [LunchViewModel] met de data over de lunches
     */
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [PartialLunchCardBinding] dat we gebruiken voor de effeciteve databinding
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
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_mainactivity, LunchDetailFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}