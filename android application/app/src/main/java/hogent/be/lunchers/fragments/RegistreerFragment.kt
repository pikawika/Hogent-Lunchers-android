package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.utils.PreferenceUtil
import kotlinx.android.synthetic.main.fragment_registreer.view.*

class RegistreerFragment : Fragment() {

    lateinit var sharedPreferences : PreferenceUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_registreer, container, false)

        setListeners(rootView)

        sharedPreferences = PreferenceUtil()


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
        /*val apiService = NetworkApi.create()

        //er is een veld leeg
        if (text_registreer_telefoon.text.toString() == ""  ||
            text_registreer_voornaam.text.toString() == ""  ||
            text_registreer_achternaam.text.toString() == ""  ||
            text_registreer_email.text.toString() == ""  ||
            text_registreer_gebruikersnaam.text.toString() == ""  ||
            text_registreer_wachtwoord.text.toString() == ""  ||
            text_registreer_bevestigwachtwoord.text.toString() == "" ) {
            Utils.makeToast(context!!, "Gelieve alle velden in te vullen")
        }

        //wws niet gelijk
        else if (text_registreer_wachtwoord.text.toString() != text_registreer_bevestigwachtwoord.text.toString()) {
            Utils.makeToast(context!!, "Wachtwoorden komen niet overeen")
        }

        //registreer
        else {
            val registreerRequest = RegistreerRequest(
                telefoonnummer = text_registreer_telefoon.text.toString(),
                voornaam = text_registreer_voornaam.text.toString(),
                achternaam = text_registreer_achternaam.text.toString(),
                email = text_registreer_email.text.toString(),
                login = RegistreerLoginRequest(gebruikersnaam = text_registreer_gebruikersnaam.text.toString(), wachtwoord = text_registreer_wachtwoord.text.toString(), rol = "klant")
            )

            val call = apiService.registreer(registreerRequest)
            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: retrofit2.Response<TokenResponse>?) {
                    if (response != null) {
                        val tokenResponse: TokenResponse? = response.body()
                        if (tokenResponse != null) {
                            sharedPreferences.setToken(tokenResponse.token)
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_container, LunchListFragment())
                                .commit()
                        } else {
                            Utils.makeToast(context!!, "Registreren mislukt.")
                        }
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Utils.makeToast(context!!, getString(R.string.network_error))
                    swipe_refresh_layout?.isRefreshing = false
                }
            })
        }*/
    }

    private fun login() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

}