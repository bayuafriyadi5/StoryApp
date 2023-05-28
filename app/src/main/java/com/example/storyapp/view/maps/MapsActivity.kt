package com.example.storyapp.view.maps

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
@ExperimentalPagingApi
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapViewModel: MapsViewModel by viewModels()
    private var token: String = ""
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        mapViewModel.checkIfTokenAvailable().observe(this){
            this.token = it ?: ""
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        getMyLocation()


        this.lifecycleScope.launchWhenResumed {
            launch {
                mapViewModel.getAllStoriesLocation(token).observe(this@MapsActivity) { result ->
                    result.onSuccess {
                        it.listStory.forEach { story ->
                            if (story.lat != null && story.lon != null) {
                                val latLng = LatLng(story.lat.toDouble(), story.lon)
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(latLng)
                                        .title(story.name)
                                        .snippet("Latitude : ${story.lat}, Longitude : ${story.lon}")
                                )
                            }
                        }
                    }
                    result.onFailure {
                        Toast.makeText(this@MapsActivity,"Cannot get data from map",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }
    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true


            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val latLng = LatLng(it.latitude, it.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8f))
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


}