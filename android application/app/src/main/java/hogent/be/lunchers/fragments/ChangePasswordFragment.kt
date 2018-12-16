package hogent.be.lunchers.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.utils.GuiUtil
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.fragment_change_passord.*

/**
 * Een [Fragment] voor het bewerken van een gebruiker zijn wachtwoord.
 */
class ChangePasswordFragment : Fragment() {

    /**
     * [AccountViewModel] met de info over aangemelde user.
     */
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_change_passord, container, false)

        //viewmodel vullen
        accountViewModel = ViewModelProviders.of(requireActivity()).get(AccountViewModel::class.java)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners() {
        //wijzig ww geklikt
        button_change_password.setOnClickListener {
            when {
                text_change_password_new_password.text.toString() != text_change_password_confirm_password.text.toString() -> MessageUtil.showToast(
                    getString(R.string.warning_passwords_not_equal)
                )
                TextUtils.isEmpty(text_change_password_new_password.text.toString()) -> MessageUtil.showToast(
                    getString(
                        R.string.warning_empty_fields
                    )
                )
                else -> {
                    accountViewModel.changePassword(text_change_password_new_password.text.toString())
                    activity!!.supportFragmentManager.popBackStack()
                }
            }
        }
    }

    /**
     * Stopt de listeners
     */
    @Suppress("UNUSED_EXPRESSION")
    private fun stopListeners() {
        //wijzig ww geklikt
        button_change_password.setOnClickListener { null }
    }

    /**
     * Stel de actionbar zijn titel in en enable back knop
     */
    override fun onResume() {
        super.onResume()
        GuiUtil.setActionBarTitle(requireActivity() as MainActivity, getString(R.string.text_change_password))
        GuiUtil.setCanPop(requireActivity() as MainActivity)
    }

    /**
     * Disable backnop
     */
    override fun onPause() {
        super.onPause()
        GuiUtil.removeCanPop(requireActivity() as MainActivity)
    }

    override fun onStart() {
        super.onStart()

        initListeners()
    }

    override fun onStop() {
        stopListeners()
        super.onStop()
    }


}