package hogent.be.lunchers.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import hogent.be.lunchers.R
import hogent.be.lunchers.fragments.LunchListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initApp()
    }

    override fun onStart() {
        super.onStart()

        initListeners()
    }

    private fun initApp() {
        setSupportActionBar(toolbar)

        bottom_navigation_view.selectedItemId = R.id.action_list

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, LunchListFragment())
            .commit()
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
        when (item.itemId) {
            R.id.action_map -> {
                //Navigatie naar kaart
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_list -> {
                //Navigatie naar lijst
                return@OnNavigationItemSelectedListener true
            }

            R.id.action_profile -> {
                //Navigatie naar profiel
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}