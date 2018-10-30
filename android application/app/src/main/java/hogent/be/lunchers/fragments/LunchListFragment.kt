package hogent.be.lunchers.fragments

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity
import hogent.be.lunchers.adapters.LunchAdapter
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.models.Tag
import hogent.be.lunchers.network.RestApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lunch_list.view.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class LunchListFragment : Fragment() {

    private var twoPane: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.lunch_list, container, false)

        if (rootView.lunch_detail_container != null) {
            twoPane = true
        }

        retrieveAllLunches()

        val lunches = createRecyclerViewDummyData()

        rootView.lunch_list.adapter = LunchAdapter(this.requireActivity() as MainActivity, lunches, twoPane)

        return rootView
    }

    private fun retrieveAllLunches() {

        val apiService = RestApi.create()
        val call = apiService.getLunches()
        Log.d("REQUEST", call.toString() + "")
        call.enqueue(object : Callback<List<Lunch>> {
            override fun onResponse(call: Call<List<Lunch>>, response: retrofit2.Response<List<Lunch>>?) {
                if (response != null) {
                    val list: List<Lunch> = response.body()!!
                    var msg: String = ""
                    for (item: Lunch in list.iterator()) {
                        msg = msg + item.naam + "\n"
                    }
                    Toast.makeText(context, "List of Lunches  \n  $msg", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<List<Lunch>>, t: Throwable) {
                Log.e(TAG, t.toString());
            }
        })

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