package hogent.be.lunchers.fragments

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import hogent.be.lunchers.R
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.network.NetworkApi
import hogent.be.lunchers.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    // Lateinit variabelen zijn standaard null, normaal mag dit niet mag in Kotlin
    // Er wordt echter vanuit gegaan dat ze in OnStart of OnResume of ... geinitialiseerd worden
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_google_maps, container, false)

        // Een fragment voor de Google map
        val mapFragment = (childFragmentManager.findFragmentById(R.id.google_map)) as SupportMapFragment
        fragmentManager?.beginTransaction()?.replace(R.id.google_map, mapFragment)?.commit()
        mapFragment.getMapAsync(this)

        // Een variabele voor het gebruiken van de locatie van de gebruiker
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        return rootView
    }

    // Functie die wordt opgeroepen nadat de map geladen is
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
    }

    override fun onMarkerClick(marker: Marker?) = false

    // Deze methode wordt opgeroepen nadat de app de gebruiker gevraagd heeft om de locatie permissie te geven
    // Indien de gebruiker permissie tot zijn/haar locatie heeft gegeven, wordt de methode setUpMap uitgevoerd
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setUpMap()
                }
                return
            }
        }
    }

    // Deze methode plaatst markers op de map via de meegegeven latitude en longitude
    // Als je er op klikt verschijnt de naam en kan je ook via Google Maps navigatie starten
    private fun placeMarkerOnMap(lat: Double, lng: Double, naam: String, beschrijving:String, imgUrl:String) {

        map.addMarker(MarkerOptions().position(LatLng(lat, lng))
                .title(naam)
                .snippet(beschrijving))
    }

    private fun getBitmapFromURL(src: String): Bitmap? {
        var bmp:Bitmap? = null
        try {
            val input:InputStream = java.net.URL(src).openStream()
            bmp = BitmapFactory.decodeStream(input)
        } catch (e:Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }
        return bmp
    }

    // Functie die de locatie van de gebruiker ophaalt en de camera naar deze locatie laat gaan
    private fun setUpMap() {
        // De gebruiker toestemming vragen voor zijn/haar locatie te gebruiken
        // Momenteel is het nog zo dat na het toestemming geven de app nog eens opnieuw opgestart moet worden
        if (checkSelfPermission(this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13f))
                //placesTest(location.latitude, location.longitude)
            }
        }

        retrieveAllLunches()
    }

    // Deze methode haalt alle lunches op en plaatst van iedere lunch de handelaar op de kaart
    private fun retrieveAllLunches() {
        val apiService = NetworkApi.create()
        val call = apiService.getAllLunches()
        call.enqueue(object : Callback<List<Lunch>> {
            override fun onResponse(call: Call<List<Lunch>>, response: retrofit2.Response<List<Lunch>>?) {
                if (response != null) {
                    val list: List<Lunch>? = response.body()
                    if (list != null) {
                        list.forEach {
                            placeMarkerOnMap(it.handelaar.locatie.latitude, it.handelaar.locatie.longitude, it.handelaar.handelsNaam, it.beschrijving, it.afbeeldingen[0].pad)
                        }
                    } else {
                        Utils.makeToast(context!!, getString(R.string.network_error))
                    }
                }
            }

            override fun onFailure(call: Call<List<Lunch>>, t: Throwable) {
                Utils.makeToast(context!!, getString(R.string.network_error))
                Log.e("NOPE", "DAT IS ER NAAAAAST ${t.message}")
            }
        })
    }




    // Functie voor het testen van de Google Places API
    // Hier wordt Volley gebruikt voor een netwerk request, deze library gebruiken we niet meer
    //private fun placesTest(lat: Double, long: Double) {
        // Instantiate the cache
        //val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        //val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        //val requestQueue = RequestQueue(cache, network).apply {
        //    start()
        //}

        //Elke API key kan slechts 1 keer gebruikt worden, we moeten er dus eentje aanmaken voor de Google map
        //en ook voor de Places API, maar we hebben voorlopig nog geen tweede key aangemaakt
        //val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=${getString(R.string.google_maps_key)}&location=$lat, $long&radius=100&type=restaurant&fields=photos,formatted_address,name,rating,opening_hours,geometry"

        // Get request
        //val jsonObjectRequest = JsonObjectRequest(
        //    Request.Method.GET, url, null,
        //    Response.Listener { response ->
        //       Toast.makeText(this, "Response: %s".format(response.toString()), Toast.LENGTH_SHORT).show()
        //    },
        //    Response.ErrorListener { error ->
        //        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        //    }
        //)

        // Add the request to the RequestQueue.
        //requestQueue.add(jsonObjectRequest)
    //}

    // Een companion object kan je zien als een statische variabele
    // In dit geval is het de request code die we proberen terug te krijgen
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}