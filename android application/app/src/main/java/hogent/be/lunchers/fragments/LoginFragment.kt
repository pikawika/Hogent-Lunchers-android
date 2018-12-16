package hogent.be.lunchers.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * Een [Fragment] waar een gebruiker zich kan aanmelden of kan doorklikken naar registreren.
 */
class LoginFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)

        initListeners(rootView)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners(fragment: View) {
        //aangemeld en parentactivity bijhouden
        val loggedIn = accountViewModel.isLoggedIn

        //indien aangemeld naar lijst gaan
        loggedIn.observe(this, Observer {
            if (loggedIn.value == true) {
                //simuleert een button click op lijst om er voor te zorgen dat juiste
                //item actief is + zet fragment etc automatisch juist
                (requireActivity() as AppCompatActivity).bottom_navigation_mainactivity.selectedItemId =
                        R.id.action_list
            }
        })

        fragment.btn_login_confirm.setOnClickListener {
            login()
        }

        fragment.btn_login_register.setOnClickListener {
            register()
        }
    }

    /**
     * Controleert de waarden en voert registratie uit
     */
    private fun register() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_mainactivity, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    /**
     * Controleert de waarden en logt in
     */
    private fun login() {
        //velden leeg
        if (TextUtils.isEmpty(text_login_username.text.toString()) && TextUtils.isEmpty(text_login_password.text.toString()))
            MessageUtil.showToast(getString(R.string.warning_empty_fields)
        )
        else accountViewModel.login(text_login_username.text.toString(), text_login_password.text.toString())
    }

    /**
     * Stel de actionbar zijn titel in
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_login))
    }


}