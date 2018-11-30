package hogent.be.lunchers.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.utils.PreferenceUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel : AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)

        setListeners(rootView)

        return rootView
    }


    private fun afmelden() {
       accountViewModel.afmelden()
    }

    private fun setListeners(rootView: View) {
        rootView.buttonLogout.setOnClickListener {
            afmelden()
        }
    }
}
