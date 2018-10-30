package hogent.be.lunchers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hogent.be.lunchers.R
import hogent.be.lunchers.fragments.LunchListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, LunchListFragment())
            .commit()
    }

}