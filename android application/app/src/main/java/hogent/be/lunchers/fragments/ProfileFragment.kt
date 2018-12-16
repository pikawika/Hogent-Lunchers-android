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
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Een [Fragment] voor het weergeven van profiel gerelateerde opties
 */
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

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners() {
        //indien aangemeld naar lijst gaan
        accountViewModel.isLoggedIn.observe(this, Observer {
            if (!accountViewModel.isLoggedIn.value!!) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })

        //afmeldknop
        btn_profile_logout.setOnClickListener {
            logOff()
        }

        //ww wijzigen knop
        btn_profile_change_password.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, ChangePasswordFragment())
                .addToBackStack(null)
                .commit()
        }

        //bekijk reservaties
        btn_profile_orders.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, OrderListFragment())
                .addToBackStack(null)
                .commit()
        }

        //voorkeuren
        btn_profile_preferences.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_mainactivity, PreferencesFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    /**
     * Stop de listeners
     */
    @Suppress("UNUSED_EXPRESSION")
    private fun stopListeners() {
        //afmeldknop
        btn_profile_logout.setOnClickListener { null }

        //ww wijzigen knop
        btn_profile_change_password.setOnClickListener { null }

        //bekijk reservaties
        btn_profile_orders.setOnClickListener { null }

        //voorkeuren
        btn_profile_preferences.setOnClickListener { null }
    }

    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_profile_title))
    }


    private fun logOff() {

        accountViewModel.logout()
    }

    override fun onStart() {
        super.onStart()

        initListeners()
    }

    override fun onStop() {
        stopListeners()
        super.onStop()
    }

}
