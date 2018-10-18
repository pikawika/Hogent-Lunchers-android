package hogent.be.lunchers

import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    //Lateinit variabelen zijn standaard null, normaal mag dit niet mag in Kotlin
    //Er wordt echter vanuit gegaan dat ze in OnStart, OnResume, ... geinitialiseerd worden
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    //Een companion object kan je zien als een statische variabele
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //Een fragment voor de Google map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_main_mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Een variabele voor het gebruiken van de locatie van de gebruiker
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    //Functie die bepaalt wat er moet gebeuren nadat de kaart geladen is
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Een marker toevoegen met Aalst als locatie en de camera er naartoe laten gaan, puur voor te testen
        // val aalst = LatLng(50.937885, 4.040956)
        // map.addMarker(MarkerOptions().position(aalst).title("Welkom in Aalst!"))
        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(aalst, 12f))

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false

    //Functie die de locatie van de gebruiker ophaalt en de camera naar deze locatie laat gaan
    private fun setUpMap() {
        //De gebruiker toestemming vragen voor zijn/haar locatie te gebruiken
        //Momenteel is het nog zo dat na het toestemming geven de app nog eens opnieuw opgestart moet worden
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13f))
                //placesTest(location.latitude, location.longitude)
            }
        }
    }

    //Functie voor het testen van de Google Places API
    private fun placesTest(lat: Double, long: Double) {
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }

        //Elke API key kan slechts 1 keer gebruikt worden, we moeten er dus eentje aanmaken voor de Google map
        //en ook voor de Places API, maar we hebben voorlopig nog geen tweede key aangemaakt
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=${getString(R.string.google_maps_key)}&location=$lat, $long&radius=100&type=restaurant&fields=photos,formatted_address,name,rating,opening_hours,geometry"

        // Get request
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Toast.makeText(this, "Response: %s".format(response.toString()), Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
            }
        )

        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest)
    }

}
