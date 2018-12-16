package hogent.be.lunchers.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.text.Editable
import android.text.TextWatcher
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
import hogent.be.lunchers.databinding.FragmentMapBinding
import hogent.be.lunchers.databinding.FragmentProfileBinding
import hogent.be.lunchers.models.Lunch
import hogent.be.lunchers.utils.MessageUtil
import hogent.be.lunchers.viewmodels.LunchViewModel
import kotlinx.android.synthetic.main.fragment_map.view.*
import kotlinx.android.synthetic.main.partial_search.view.*


class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    /**
     * [LunchViewModel] met de data over account
     */
    private lateinit var lunchViewModel: LunchViewModel

    /**
     * De [FragmentProfileBinding] dat we gebruiken voor de effeciteve databinding
     */
    private lateinit var binding: FragmentMapBinding

    // Lateinit variabelen zijn standaard null, normaal mag dit niet in Kotlin
    // Er wordt echter vanuit gegaan dat ze in OnStart of OnResume of ... geinitialiseerd worden
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        //viewmodel vullen
        lunchViewModel = ViewModelProviders.of(requireActivity()).get(LunchViewModel::class.java)

        val rootView = binding.root
        binding.lunchViewModel = lunchViewModel
        binding.setLifecycleOwner(activity)

        // Een fragment voor de Google map
        val mapFragment = (childFragmentManager.findFragmentById(R.id.map_google_maps_fragment)) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Een variabele voor het gebruiken van de location van de gebruiker
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        childFragmentManager
            .beginTransaction()
            .replace(R.id.map_selected_lunch, PartialLunchCardFragment())
            .commit()

        initListeners(rootView)

        return rootView
    }

    /**
     * Instantieer de listeners
     */
    private fun initListeners(rootView: View) {
        rootView.map_search.txt_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                lunchViewModel.search(s.toString())
            }
        })
    }

    // Functie die wordt opgeroepen nadat de map geladen is
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()
    }

    //zet de geselecteerde lunch op de geklikte marker waardoor partial onderaan tevoorschijn komt
    //en zet de camera zodat de marker in het midden staat met behoud van zoom level
    override fun onMarkerClick(clickedMarker: Marker?): Boolean {
        lunchViewModel.setSelectedLunch(clickedMarker!!.title.toInt())
        clickedMarker.hideInfoWindow()
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(clickedMarker.position, map.cameraPosition.zoom))
        return true
    }

    // Deze methode wordt opgeroepen nadat de app de gebruiker gevraagd heeft om de location permissie te geven
    // Indien de gebruiker permissie tot zijn/haar location heeft gegeven, wordt de methode setUpMap uitgevoerd
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
    // Als je er op klikt verschijnt de name en kan je ook via Google Maps navigatie starten
    private fun placeMarkerOnMap(lat: Double, lng: Double, id: String) {
        map.addMarker(
            MarkerOptions().position(LatLng(lat, lng))
                .title(id)
        )
    }

    // Functie die de location van de gebruiker ophaalt en de camera naar deze location laat gaan
    private fun setUpMap() {
        // De gebruiker toestemming vragen voor zijn/haar location te gebruiken
        // Momenteel is het nog zo dat na het toestemming geven de app nog eens opnieuw opgestart moet worden
        if (checkSelfPermission(
                this.requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            MessageUtil.showToast("Geef locatietoestemming en probeer opnieuw")
            return
        }

        map.isMyLocationEnabled = true

        retrieveAllLunches()

        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->
            if (location != null && lunchViewModel.getSelectedLunch().value == null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }

        if (lunchViewModel.getSelectedLunch().value != null) {
            val currentLatLng = LatLng(
                lunchViewModel.getSelectedLunch().value!!.merchant.location.latitude,
                lunchViewModel.getSelectedLunch().value!!.merchant.location.longitude
            )
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
        }
    }

    // Deze methode haalt alle lunches op en plaatst van iedere lunch de merchant op de kaart
    private fun retrieveAllLunches() {
        val list = lunchViewModel.getFilteredLunches()


        list.observe(this, Observer {
            putMarkersOnMap(list.value!!)
        })
    }

    private fun putMarkersOnMap(lunches: List<Lunch>) {
        //alle markers reeds op de kaart wegdoen
        map.clear()
        lunches.forEach {
            placeMarkerOnMap(
                it.merchant.location.latitude,
                it.merchant.location.longitude,
                it.lunchId.toString()
            )
        }

    }

    // Een companion object kan je zien als een statische variabele
    // In dit geval is het de request code die we proberen terug te krijgen
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}