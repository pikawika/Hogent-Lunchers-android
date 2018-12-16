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
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
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
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)

        val rootView = binding.root

        //databinding
        binding.accountViewModel = accountViewModel
        binding.setLifecycleOwner(activity)

        setListeners(rootView)

        return rootView
    }

    private fun setListeners(rootView: View) {
        //indien aangemeld naar lijst gaan
        accountViewModel.getIsLoggedIn().observe(this, Observer {
            if (!accountViewModel.getIsLoggedIn().value!!) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })

        //afmeldknop
        rootView.btn_profile_logout.setOnClickListener {
            afmelden()
        }

        //ww wijzigen knop
        rootView.btn_profile_change_password.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, ChangePasswordFragment())
                .addToBackStack(null)
                .commit()
        }

        //bekijk reservaties
        rootView.btn_profile_orders.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, OrderListFragment())
                .addToBackStack(null)
                .commit()
        }

        //voorkeuren
        rootView.btn_profile_preferences.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_profile_title))
    }


    private fun afmelden() {

        accountViewModel.afmelden()
    }


}
