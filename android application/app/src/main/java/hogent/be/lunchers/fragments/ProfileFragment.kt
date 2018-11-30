package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.utils.PreferenceUtil
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        rootView.buttonLogout.setOnClickListener {
            PreferenceUtil().deleteToken()
            fragmentManager!!.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
        }

        return rootView
    }



    companion object {
        fun newInstance() = ProfileFragment()
    }
}
