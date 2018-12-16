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
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegistreerFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel : AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)

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
                parentActivity.bottom_navigation_mainactivity.selectedItemId = R.id.action_list
            }
        })

        return rootView
    }

    fun setListeners(fragment: View) {
        fragment.btn_register_login.setOnClickListener {
            login()
        }

        fragment.btn_register_confirm.setOnClickListener {
            registreer()
        }
    }

    private fun registreer() {
        //er is een veld leeg
        if (text_register_phone_number.text.toString() == ""  ||
            text_register_first_name.text.toString() == ""  ||
            text_register_last_name.text.toString() == ""  ||
            text_register_email.text.toString() == ""  ||
            text_register_username.text.toString() == ""  ||
            text_register_password.text.toString() == ""  ||
            text_register_confirm_password.text.toString() == "" ) {
            MessageUtil.showToast("Gelieve alle velden in te vullen")
        }

        //wws niet gelijk
        else if (text_register_password.text.toString() != text_register_confirm_password.text.toString()) {
            MessageUtil.showToast("Wachtwoorden komen niet overeen")
        }

        //registreerKlant
        else {
            accountViewModel.registreerKlant(text_register_phone_number.text.toString(),
                    text_register_first_name.text.toString(),
                    text_register_last_name.text.toString(),
                    text_register_email.text.toString(),
                    text_register_username.text.toString(),
                    text_register_password.text.toString())
        }
    }

    private fun login() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_mainactivity, LoginFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.text_register)
        MainActivity.setCanpop(true)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        MainActivity.setCanpop(false)
    }

}