package hogent.be.lunchers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import hogent.be.lunchers.adapters.SimpleItemRecyclerViewAdapter
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.Tag
import kotlinx.android.synthetic.main.activity_lunch_list.*
import kotlinx.android.synthetic.main.lunch_list.*
import java.util.*

class LunchListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (lunch_detail_container != null) {
            twoPane = true
        }

        setupRecyclerView(lunch_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val lunches = createRecyclerViewDummyData()
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, lunches, twoPane)
    }

    //TIJDELIJK: deze methode maakt dummy data aan om de recyclerview te vullen
    private fun createRecyclerViewDummyData(): List<Lunch> {
        return listOf(
            Lunch(
                lunchId = 0,
                naam = "Friet met stoverij",
                prijs = 12.95,
                beschrijving = "Een traditionele Belgische maaltijd, met liefde verzorgd en wordt geserveerd met een heerlijk vers slaatje.",
                afbeeldingen = listOf(R.drawable.eten),
                ingredienten = listOf(""),
                beginDatum = Calendar.getInstance().time,
                eindDatum = Calendar.getInstance().time,
                tags = listOf(Tag(naam = "Vegan", kleur = "Groen"))
            ),
            Lunch(
                lunchId = 1,
                naam = "Krabburger",
                prijs = 13.99,
                beschrijving = "De naam kennen jullie wel, maar hebben jullie er ooit al echt eens eentje gegeten? Geniet van een lekkere hamburger met krab tijdens je welverdiende lunchpauze!",
                afbeeldingen = listOf(R.drawable.hamburger),
                ingredienten = listOf(""),
                beginDatum = Calendar.getInstance().time,
                eindDatum = Calendar.getInstance().time,
                tags = listOf(Tag(naam = "Vegan", kleur = "Groen"))
            ),
            Lunch(
                lunchId = 2,
                naam = "Chicken wings",
                prijs = 9.98,
                beschrijving = "Sommige mensen zeggen dat kippenvleugeltjes geen echte maaltijd zijn. Wij beweren iets anders. Enkel deze week all-you-can-eat kippenvleugels voor een prijsje dat net zo zacht is als onze kip!",
                afbeeldingen = listOf(R.drawable.kippevleugels),
                ingredienten = listOf(""),
                beginDatum = Calendar.getInstance().time,
                eindDatum = Calendar.getInstance().time,
                tags = listOf(Tag(naam = "Vegan", kleur = "Groen"))
            ),
            Lunch(
                lunchId = 3,
                naam = "Kleurrijke lunch",
                prijs = 14.99,
                beschrijving = "Kan jouw werkdag wel een boost aan positiviteit gebruiken? Geniete van een heerlijke lunch met eten dat net zo goed smaakt als dat het kleurrijk is.",
                afbeeldingen = listOf(R.drawable.kleurstoffen),
                ingredienten = listOf(""),
                beginDatum = Calendar.getInstance().time,
                eindDatum = Calendar.getInstance().time,
                tags = listOf(Tag(naam = "Vegan", kleur = "Groen"))
            ),
            Lunch(
                lunchId = 4,
                naam = getString(R.string.lunch_tekst_naam),
                prijs = 16.95,
                beschrijving = getString(R.string.lunch_tekst_beschrijving),
                afbeeldingen = listOf(R.drawable.wavanalles),
                ingredienten = listOf(""),
                beginDatum = Calendar.getInstance().time,
                eindDatum = Calendar.getInstance().time,
                tags = listOf(Tag(naam = "Vegan", kleur = "Groen"))
            )
        )
    }

}
