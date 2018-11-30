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
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_registreer.*
import kotlinx.android.synthetic.main.fragment_registreer.view.*

class RegistreerFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel : AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_registreer, container, false)

        setListeners(rootView)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)

        //aangemeld en parentactivity bijhouden
        val aangemeld = accountViewModel.getIsAangmeld()
        val parentActivity = (activity as AppCompatActivity)

        //indien aangemeld naar lijst gaan
        aangemeld.observe(this, Observer {
            if (aangemeld.value!!) {
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, LunchListFragment())
                    .commit()
            }
        })

        return rootView
    }

    fun setListeners(fragment: View) {
        fragment.button_registreer_login.setOnClickListener {
            login()
        }

        fragment.button_registreer_registreren.setOnClickListener {
            registreer()
        }
    }

    private fun registreer() {
        //er is een veld leeg
        if (text_registreer_telefoon.text.toString() == ""  ||
            text_registreer_voornaam.text.toString() == ""  ||
            text_registreer_achternaam.text.toString() == ""  ||
            text_registreer_email.text.toString() == ""  ||
            text_registreer_gebruikersnaam.text.toString() == ""  ||
            text_registreer_wachtwoord.text.toString() == ""  ||
            text_registreer_bevestigwachtwoord.text.toString() == "" ) {
            MessageUtil.showToast("Gelieve alle velden in te vullen")
        }

        //wws niet gelijk
        else if (text_registreer_wachtwoord.text.toString() != text_registreer_bevestigwachtwoord.text.toString()) {
            MessageUtil.showToast("Wachtwoorden komen niet overeen")
        }

        //registreerKlant
        else {
            accountViewModel.registreerKlant(text_registreer_telefoon.text.toString(),
                    text_registreer_voornaam.text.toString(),
                    text_registreer_achternaam.text.toString(),
                    text_registreer_email.text.toString(),
                    text_registreer_gebruikersnaam.text.toString(),
                    text_registreer_wachtwoord.text.toString())
        }
    }

    private fun login() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

}