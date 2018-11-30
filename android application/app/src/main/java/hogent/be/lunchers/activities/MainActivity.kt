package hogent.be.lunchers.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import hogent.be.lunchers.R
import hogent.be.lunchers.databinding.ActivityMainBinding
import hogent.be.lunchers.fragments.LoginFragment
import hogent.be.lunchers.fragments.LunchListFragment
import hogent.be.lunchers.fragments.MapsFragment
import hogent.be.lunchers.utils.PreferenceUtil
import hogent.be.lunchers.fragments.ProfileFragment
import hogent.be.lunchers.viewmodels.AccountViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences : PreferenceUtil

    /**
     * De [AccountViewModel] dat we gebruiken voord de data voor databinding
     */
    private lateinit var accountViewModel: AccountViewModel

    /**
     * De [ActivityMainBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //context instellen voor globaal gebruik
        instance = this

        //main activity binden
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        binding.accountViewModel = accountViewModel
        binding.setLifecycleOwner(this)

        sharedPreferences = PreferenceUtil()

        initApp()
    }

    override fun onStart() {
        super.onStart()

        initListeners()
    }

    private fun initApp() {
        setSupportActionBar(toolbar)

        bottom_navigation_view.selectedItemId = R.id.action_list

        if (sharedPreferences.getToken() != ""){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LunchListFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }

    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
            supportActionBar?.title = getString(R.string.app_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        bottom_navigation_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        supportFragmentManager.popBackStack()
        when (item.itemId) {
            R.id.action_map -> {
                supportActionBar?.title = "Restaurants in de buurt"
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MapsFragment())
                    .commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_list -> {
                supportActionBar?.title = getString(R.string.app_name)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LunchListFragment())
                    .commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_profile -> {
                supportActionBar?.title = "Profiel"
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    companion object {
        //voor globaal gebruik van context
        //handig om toasts van eender waar te doen en gebruik in andere utils
        private var instance: MainActivity? = null

        /**
         * returnt de [Context] van de app zijn MainActivity
         */
        fun getContext(): Context {
            return instance!!.applicationContext
        }
    }

}