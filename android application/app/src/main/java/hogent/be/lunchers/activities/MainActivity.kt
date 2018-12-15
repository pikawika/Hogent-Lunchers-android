package hogent.be.lunchers.activities

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.R
import hogent.be.lunchers.databinding.ActivityMainBinding
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.fragments.*
import hogent.be.lunchers.utils.PreferenceUtil
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: PreferenceUtil

    /**
     * De [AccountViewModel] dat we gebruiken voord de data voor databinding
     */
    private lateinit var accountViewModel: AccountViewModel

    /**
     * De [LunchViewModel] dat we gebruiken voord de data voor databinding
     */
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [ActivityMainBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Client voor het ophalen van de locatie van een user
     */
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //context instellen voor globaal gebruik
        instance = this

        //main activity binden
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        lunchViewModel = ViewModelProviders.of(this).get(LunchViewModel::class.java)
        binding.accountViewModel = accountViewModel
        binding.setLifecycleOwner(this)

        sharedPreferences = PreferenceUtil()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if (lunchViewModel.getSelectedFilter() == FilterEnum.DISTANCE)
            lunchesFromLocation()

        setSupportActionBar(toolbar_mainactivity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menu van de toolbar instellen
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        //men moet wachten tot toolbar is ingesteld om hem te kunnen configureren in onze fragments, deze heeft rare lifecycle.
        showBootPage()

        return true
    }

    override fun onStart() {
        super.onStart()

        initListeners()
    }

    private fun showBootPage() {
        if (accountViewModel.getIsAangmeld().value!!) {
            var fragment: Fragment
            when (accountViewModel.getDefaultBootPage()) {
                PageEnum.MAP -> {
                    fragment = MapsFragment()
                    bottom_navigation_mainactivity.selectedItemId = R.id.action_map
                }
                PageEnum.PROFILE -> {
                    fragment = ProfileFragment()
                    bottom_navigation_mainactivity.selectedItemId = R.id.action_profile
                }
                PageEnum.ORDERSLIST -> {
                    fragment = OrderListFragment()
                    bottom_navigation_mainactivity.selectedItemId = R.id.action_profile
                }
                else -> {
                    fragment = LunchListFragment()
                    bottom_navigation_mainactivity.selectedItemId = R.id.action_list
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_mainactivity, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            //locatie toegang vragen bij eerste keer openen app (aanmeld scherm)
            if (PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_mainactivity, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    //fysieke back button ingedruk
    override fun onBackPressed() {
        //kijk of je mag terug gaan anders poppen
        if (canPop)
            supportFragmentManager.popBackStackImmediate()
        else
            finish()
    }

    private fun initListeners() {
        toolbar_mainactivity.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
            supportActionBar?.title = getString(R.string.app_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        bottom_navigation_mainactivity.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_map -> {
                supportActionBar?.title = "Restaurants in de buurt"
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, MapsFragment())
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_list -> {
                supportActionBar?.title = getString(R.string.app_name)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, LunchListFragment())
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_profile -> {
                supportActionBar?.title = "Profiel"
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, ProfileFragment())
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //het item dat gelklikt is uit de toolbar
        //dit id moet in theorie altijd ingevuld zijn want enkel dan weet je wat aangeduid en kan je bijhorende actie uitvoeren
        when (item?.itemId) {
            R.id.ab_filter_prijs_oplopend -> {
                lunchViewModel.setSelectedFilter(FilterEnum.PRICELOWEST)
                return super.onOptionsItemSelected(item)
            }

            R.id.ab_filter_prijs_aflopend -> {
                lunchViewModel.setSelectedFilter(FilterEnum.PRICEHIGHEST)
                return super.onOptionsItemSelected(item)
            }

            R.id.ab_filter_afstand -> {
                lunchesFromLocation()
                return super.onOptionsItemSelected(item)
            }

            R.id.ab_filter_nieuwste -> {
                lunchViewModel.setSelectedFilter(FilterEnum.RECENT)
                return super.onOptionsItemSelected(item)
            }

            //default
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun lunchesFromLocation() {
        if (PermissionChecker.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            MessageUtil.showToast("Geef locatietoestemming en probeer opnieuw")
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location?.latitude == null || location?.longitude == null)
                    MessageUtil.showToast("Locatie niet beschikbaar, recentste lunches worden getoond.")

                lunchViewModel.setSelectedFilter(
                    FilterEnum.DISTANCE,
                    location?.latitude ?: 0.00,
                    location?.longitude ?: 0.00
                )
            }
    }

    companion object {
        //voor globaal gebruik van context
        //handig om toasts van eender waar te doen en gebruik in andere utils
        private var instance: MainActivity? = null
        private var canPop: Boolean = false

        /**
         * returnt de [Context] van de app zijn MainActivity
         */
        fun getContext(): Context {
            return instance!!.applicationContext
        }

        /**
         * returnt de [Context] van de app zijn MainActivity
         */
        fun setCanpop(boolean: Boolean) {
            canPop = boolean
        }
    }
}