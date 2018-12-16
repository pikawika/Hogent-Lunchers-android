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
import hogent.be.lunchers.R
import hogent.be.lunchers.databinding.ActivityMainBinding
import hogent.be.lunchers.enums.FilterEnum
import hogent.be.lunchers.enums.PageEnum
import hogent.be.lunchers.fragments.*
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.AccountViewModel
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * De [AccountViewModel] met informatie over de *aangemelde gebruiker*.
     */
    private lateinit var accountViewModel: AccountViewModel

    /**
     * De [LunchViewModel] met informatie over de *lunches*.
     */
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [ActivityMainBinding] dat we gebruiken voor de effeciteve databinding met de [MainActivity]
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Client voor het ophalen van de locatie van de gebruiker
     */
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //context instellen voor globaal gebruik
        instance = this

        //viewmodels vullen
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        lunchViewModel = ViewModelProviders.of(this).get(LunchViewModel::class.java)

        //main activity binden
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.accountViewModel = accountViewModel
        binding.setLifecycleOwner(this)

        //locatieclient ophalen
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //indien defaultfilter op afstand is moet de locatie opgevraagd worden
        if (lunchViewModel.selectedFilter == FilterEnum.DISTANCE)
            lunchesFromLocation()

        //toolbar instellen als actionbar
        setSupportActionBar(toolbar_mainactivity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menu van de toolbar instellen
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        //laad de eerste fragment in adhv de door gebruiker ingestelde bootpage.
        //moeten tot hier wachten aangezien we toolbar zijn tekst instellen en toolbar een aparte (zeer late)
        //instantiatie heeft
        showBootPage()

        return true
    }

    override fun onStart() {
        super.onStart()

        //listeners instantiëren
        initListeners()
    }

    /**
     * Stelt de initieele fragment in adhv de door de gebruiker ingestelde bootPage.
     *
     * Indien niet aangemeld zal naar het login scherm gegaan worden.
     */
    private fun showBootPage() {
        // indien aangemeld uit vm halen welke bootpagina is en die tonen
        // en higlight nav item goed instellen
        if (accountViewModel.isLoggedIn.value!!) {
            val fragment: Fragment
            when (accountViewModel.getDefaultBootPage()) {
                PageEnum.MAP -> {
                    fragment = MapsFragment()
                    bottom_navigation_mainactivity.selectedItemId = R.id.action_map
                }
                PageEnum.PROFILE -> {
                    fragment = ProfileFragment()
                    bottom_navigation_mainactivity.selectedItemId = R.id.action_profile
                }
                PageEnum.ORDERS_LIST -> {
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
            // location toegang vragen bij eerste keer openen app (aanmeld scherm)
            // popup enkel indien nog niet goedgekeurd geweest op toestel
            if (PermissionChecker.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
                MessageUtil.showToast(getString(R.string.warning_we_need_location_acces))
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_mainactivity, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    //fysieke back button ingedruk
    override fun onBackPressed() {
        //kijk of je mag terug gaan en al dan niet poppen anders app sluiten
        if (canPop)
            supportFragmentManager.popBackStackImmediate()
        else
            finish()
    }

    /**
     * Instantieer de listeners van de [MainActivity].
     */
    private fun initListeners() {
        //pop backstack als op terugknop in toolbar geklikt wordt
        toolbar_mainactivity.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        bottom_navigation_mainactivity.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    /**
     * Listener voor de bottom navigation view dat er voor zorgt dat juiste fragment getoond wordt on click.
     */
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_map -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, MapsFragment())
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_list -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, LunchListFragment())
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_profile -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_mainactivity, ProfileFragment())
                    .addToBackStack(null)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * Behandeld het klikken op een item uit de toolbar.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //het item dat gelklikt is uit de toolbar
        //dit id moet in theorie altijd ingevuld zijn want enkel dan weet je wat aangeduid en kan je bijhorende actie uitvoeren
        when (item?.itemId) {
            R.id.ab_filter_price_lowest -> {
                lunchViewModel.setSelectedFilter(FilterEnum.PRICE_LOWEST)
                return super.onOptionsItemSelected(item)
            }

            R.id.ab_filter_price_highest -> {
                lunchViewModel.setSelectedFilter(FilterEnum.PRICE_HIGHEST)
                return super.onOptionsItemSelected(item)
            }

            R.id.ab_filter_distance -> {
                lunchesFromLocation()
                return super.onOptionsItemSelected(item)
            }

            R.id.ab_filter_newest -> {
                lunchViewModel.setSelectedFilter(FilterEnum.RECENT)
                return super.onOptionsItemSelected(item)
            }

            //default
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * Haalt de lunches op adhv locatie (dichtste eerst), hiervoor is locatietoestemming nodig.
     */
    private fun lunchesFromLocation() {
        //check permissie en toon popup indien geen
        if (PermissionChecker.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            //permissie geweigerd
            MessageUtil.showToast(getString(R.string.warning_we_need_location_acces))
        }
        //permissie aanwezig
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location?.latitude == null)
                    //gps staat uit/ geen locatie
                    MessageUtil.showToast(getString(R.string.error_no_gps_signal))
                else {
                    //long en lat gekregen
                    lunchViewModel.setSelectedFilter(
                        FilterEnum.DISTANCE,
                        location.latitude,
                        location.longitude
                    )
                }
            }
    }

    /**
     * Companian object van de mainactivity om globale communicatie mogelijk te maken.
     */
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