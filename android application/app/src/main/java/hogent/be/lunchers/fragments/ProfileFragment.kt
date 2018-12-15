package hogent.be.lunchers.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.databinding.FragmentProfileBinding
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel: AccountViewModel

    /**
     * De [FragmentProfileBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(activity!!).get(AccountViewModel::class.java)

        val rootView = binding.root
        binding.accountViewModel = accountViewModel
        binding.setLifecycleOwner(activity)

        //aangemeld bijhouden
        val aangemeld = accountViewModel.getIsAangmeld()

        //indien aangemeld naar lijst gaan
        aangemeld.observe(this, Observer {
            if (!aangemeld.value!!) {
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })

        setListeners(rootView)

        return rootView
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.title = "Profiel"
    }


    private fun afmelden() {

        accountViewModel.afmelden()
    }

    private fun setListeners(rootView: View) {
        //afmeldknop
        rootView.buttonLogout.setOnClickListener {
            afmelden()
        }
        //ww wijzigen knop
        rootView.buttonChangePassword.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, ChangePasswordFragment())
                .addToBackStack(null)
                .commit()
        }
        rootView.buttonReservations.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, OrderListFragment())
                .addToBackStack(null)
                .commit()
        }
        rootView.profile_button_preferences.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
