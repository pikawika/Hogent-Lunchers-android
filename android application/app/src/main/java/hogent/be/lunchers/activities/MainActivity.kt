package hogent.be.lunchers.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ListFragment
import android.support.v7.app.AppCompatActivity
import hogent.be.lunchers.R
import hogent.be.lunchers.fragments.LoginFragment
import hogent.be.lunchers.fragments.LunchListFragment
import hogent.be.lunchers.fragments.MapsFragment
import hogent.be.lunchers.utils.PreferenceUtil
import hogent.be.lunchers.utils.Utils
import hogent.be.lunchers.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences : PreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceUtil(this)

        //context instellen voor globaal gebruik
        instance = this

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