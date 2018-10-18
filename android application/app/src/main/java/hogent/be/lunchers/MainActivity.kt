package hogent.be.lunchers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout.VERTICAL
import hogent.be.lunchers.adapters.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //We maken gebruik van een custom app bar
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_layout)

        //Het inladen van de gegevens in de RecyclerView
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerview = recyclerview_main_lunches

        val lunches = intArrayOf(R.drawable.eten, R.drawable.hamburger, R.drawable.kippevleugels, R.drawable.kleurstoffen, R.drawable.wavanalles)

        val names = arrayOf("Eten", "Hamburger", "Kippevleugels", "Kleurstoffen", "Wavanalles")

        val lManager = LinearLayoutManager(this, VERTICAL, false)

        recyclerview.layoutManager = lManager

        recyclerview.adapter = MyAdapter(lunches, names, this)
    }

}
