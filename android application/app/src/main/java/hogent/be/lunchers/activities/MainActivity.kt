package hogent.be.lunchers.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import hogent.be.lunchers.R
import hogent.be.lunchers.fragments.LunchListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("LOL", "HIER ZIJN WE")

        val fragment = LunchListFragment()

        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

}