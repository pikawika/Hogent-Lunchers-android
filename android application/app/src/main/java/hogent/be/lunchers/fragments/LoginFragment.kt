package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.networkResponses.TokenResponse
import hogent.be.lunchers.network.NetworkApi
import hogent.be.lunchers.networkRequests.LoginRequest
import hogent.be.lunchers.utils.PreferenceUtil
import hogent.be.lunchers.utils.Utils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.lunch_list.*
import retrofit2.Call
import retrofit2.Callback

class LoginFragment : Fragment() {

    lateinit var sharedPreferences : PreferenceUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)

        setListeners(rootView)

        sharedPreferences = PreferenceUtil(context!!)


        return rootView
    }

    fun setListeners(fragment: View) {
        fragment.button_login_login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        swipe_refresh_layout?.isRefreshing = true

        val apiService = NetworkApi.create()
        val call = apiService.login(LoginRequest(gebruikersnaam = text_login_gebruikersnaam.text.toString(), wachtwoord = text_login_wachtwoord.text.toString()))
        call.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: retrofit2.Response<TokenResponse>?) {
                if (response != null) {
                    val tokenResponse: TokenResponse? = response.body()
                    if (tokenResponse != null) {
                        sharedPreferences.setToken(tokenResponse.token)
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, LunchListFragment())
                            .addToBackStack(null)
                            .commit()
                    } else {
                        Utils.makeToast(context!!, "Aanmelden mislukt.")
                    }
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Utils.makeToast(context!!, getString(R.string.network_error))
                swipe_refresh_layout?.isRefreshing = false
            }
        })
    }

}