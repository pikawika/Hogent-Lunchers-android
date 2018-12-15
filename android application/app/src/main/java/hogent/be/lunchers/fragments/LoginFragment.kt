package hogent.be.lunchers.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel : AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        setListeners(rootView)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)

        //aangemeld en parentactivity bijhouden
        val aangemeld = accountViewModel.getIsAangmeld()
        val parentActivity = (activity as AppCompatActivity)

        //indien aangemeld naar lijst gaan
        aangemeld.observe(this, Observer {
            if (aangemeld.value == true) {
                //simuleert een button click op lijst om er voor te zorgen dat juiste
                //item actief is + zet fragment etc automatisch juist
                parentActivity.bottom_navigation_view.selectedItemId = R.id.action_list
            }
        })

        return rootView
    }

    private fun setListeners(fragment: View) {
        fragment.button_login.setOnClickListener {
            login()
        }

        fragment.button_login_register.setOnClickListener {
            registreer()
        }
    }

    private fun registreer() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RegistreerFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun login() {
        accountViewModel.login(text_login_username.text.toString(), text_login_password.text.toString())
    }

}