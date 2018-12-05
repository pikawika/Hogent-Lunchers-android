package hogent.be.lunchers.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_change_passord.*
import kotlinx.android.synthetic.main.fragment_change_passord.view.*

class ChangePasswordFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel : AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_change_passord, container, false)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(activity!!).get(AccountViewModel::class.java)

        setListeners(rootView)

        return rootView
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPause() {
        super.onPause()

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setListeners(fragment: View) {
        fragment.button_change_password.setOnClickListener {
            if (text_changepw_newpw.text.toString() != text_changepw_newpc_confirm.text.toString()) {
                MessageUtil.showToast("Wachtwoorden komen niet overeen")
            }
            else {
                accountViewModel.changePassword(text_changepw_newpw.text.toString())
            }
        }
    }
}