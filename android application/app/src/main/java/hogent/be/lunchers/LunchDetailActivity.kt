package hogent.be.lunchers

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import com.bumptech.glide.Glide
import hogent.be.lunchers.model.Lunch
import kotlinx.android.synthetic.main.activity_lunchdetail.*

class LunchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunchdetail)

        //We maken gebruik van een custom app bar
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_layout)

        val lunch: Lunch = (intent?.extras?.get("selectedLunch") as? Lunch)!!

        prepareLunch(lunch)
    }

    //Functie om de gegevens van de lunch in de pagina te laden
    @SuppressLint("SetTextI18n")
    private fun prepareLunch(lunch: Lunch) {
        Glide.with(this).load(lunch.afbeeldingen[0]).into(imageview_lunch_detail_afbeelding)
        textview_lunch_detail_naam.text = lunch.naam
        textview_lunch_detail_prijs.text = "€ ${lunch.prijs}"
        textview_lunch_detail_beschrijving.text = lunch.beschrijving
    }
}
