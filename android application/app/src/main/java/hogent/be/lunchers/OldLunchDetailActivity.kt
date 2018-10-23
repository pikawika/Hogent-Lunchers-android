package hogent.be.lunchers

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import hogent.be.lunchers.models.Lunch
import kotlinx.android.synthetic.main.activity_lunchdetail.*

class OldLunchDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunchdetail)

        val lunch: Lunch = (intent?.extras?.get("selectedLunch") as? Lunch)!!

        //Dit zorgt ervoor dat je een back button hebt op de lunch detail pagina
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = lunch.naam

        prepareLunch(lunch)
    }

    override fun onStart() {
        super.onStart()

        button_lunch_detail_reserveren.setOnClickListener{
            Toast.makeText(this, "Reserveren is momenteel nog niet mogelijk.", Toast.LENGTH_SHORT).show()
        }
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
