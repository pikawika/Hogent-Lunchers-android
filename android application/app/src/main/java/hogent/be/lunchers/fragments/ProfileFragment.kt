package hogent.be.lunchers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hogent.be.lunchers.R
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.network.NetworkApi
import hogent.be.lunchers.utils.Utils
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback


class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout voor deze fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getUserInfo()
    }

   private fun getUserInfo(){
       //TODO: username uit token halen wanneer authenticatie op punt staat
       this.username.text = "Jodi De Loof"
   }


}
