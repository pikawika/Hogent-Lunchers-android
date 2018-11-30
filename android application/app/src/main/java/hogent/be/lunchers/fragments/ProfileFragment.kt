package hogent.be.lunchers.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.utils.PreferenceUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    /**
     * [AccountViewModel] met de data over account
     */
    //Globaal ter beschikking gesteld aangezien het mogeiljks later nog in andere functie dan onCreateView wenst te worden
    private lateinit var accountViewModel : AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)

        //aangemeld en parentactivity bijhouden
        val aangemeld = accountViewModel.getIsAangmeld()
        val parentActivity = (activity as AppCompatActivity)

        //indien aangemeld naar lijst gaan
        aangemeld.observe(this, Observer {
            if (!aangemeld.value!!) {
                parentActivity.supportFragmentManager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
            }
        })

        setListeners(rootView)

        return rootView
    }


    private fun afmelden() {
       accountViewModel.afmelden()
    }

    private fun setListeners(rootView: View) {
        rootView.buttonLogout.setOnClickListener {
            afmelden()
        }
    }
}
