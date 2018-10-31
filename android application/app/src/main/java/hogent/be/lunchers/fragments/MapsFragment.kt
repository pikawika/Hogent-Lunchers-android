package hogent.be.lunchers.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hogent.be.lunchers.R
import hogent.be.lunchers.activities.MainActivity

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_google_maps, container, false)

        (activity as MainActivity).supportActionBar?.title = "Restaurants in de buurt"

        val mapFragment = (childFragmentManager.findFragmentById(R.id.google_map)) as SupportMapFragment
        fragmentManager?.beginTransaction()?.replace(R.id.google_map, mapFragment)?.commit()
        mapFragment.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}