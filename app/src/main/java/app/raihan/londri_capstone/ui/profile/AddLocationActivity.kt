package app.raihan.londri_capstone.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivityAddLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale
@Suppress("DEPRECATION")
class AddLocationActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityAddLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var selectedLocation : LatLng? = null
    private var currentAddress: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnClickListener{finish()}

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.saveBtn.setOnClickListener{
            val intent = Intent()
            intent.putExtra("keyLocation", currentAddress)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        mMap.setOnMapClickListener(this)

        getMyLocation()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLocation()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLocation()
                }

                else -> {
                    // No location access granted.
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    showStartMarker(location)
                    currentAddress =
                        getCurrentAddress(this, location.latitude, location.longitude)
                    binding.alamatUser.text = currentAddress
                } else {
                    Toast.makeText(
                        this@AddLocationActivity,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun showStartMarker(location: Location) {
        val startLocation = LatLng(location.latitude, location.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(startLocation)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 17f))
        selectedLocation = startLocation
    }

    private fun getCurrentAddress(context: Context, lat: Double, long: Double): String {
        val geocode = Geocoder(context, Locale.getDefault())
        val listAddress = geocode.getFromLocation(lat, long, 1)
        currentAddress = listAddress?.get(0)?.getAddressLine(0)
        currentAddress ?: "Cannot access your address"

        return currentAddress ?: "Cannot access your address"
    }

    override fun onMapClick(latLng: LatLng) {
        mMap.clear()

        mMap.addMarker(MarkerOptions().position(latLng))

        selectedLocation = latLng

        currentAddress = selectedLocation?.let {
            getCurrentAddress(this, it.latitude, it.longitude)
        } ?: "Cannot access the address for the selected location"

        binding.alamatUser.text = currentAddress
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}