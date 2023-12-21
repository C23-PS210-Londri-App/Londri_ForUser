package app.raihan.londri_capstone.ui.signup

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivitySignupBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.MainActivity
import app.raihan.londri_capstone.ui.login.LoginActivity
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
class SignupActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitySignupBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var addressLat : String
    private lateinit var addressLong : String
    private var selectedLocation: LatLng? = null
    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            val moveIntent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(moveIntent)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        registerAction()
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
                    val currentAddress =
                        getCurrentAddress(this, location.latitude, location.longitude)
                    binding.tvAddress.text = currentAddress
                } else {
                    Toast.makeText(
                        this@SignupActivity,
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
        val currentAddress = listAddress?.get(0)?.getAddressLine(0)
        currentAddress ?: "Cannot access your address"
        
        addressLat = lat.toString()
        addressLong = long.toString()

        return currentAddress ?: "Cannot access your address"
    }

    override fun onMapClick(latLng: LatLng) {
        mMap.clear()

        mMap.addMarker(MarkerOptions().position(latLng))
        selectedLocation = latLng

        val currentAddress = selectedLocation?.let {
            getCurrentAddress(this, it.latitude, it.longitude)
        } ?: "Cannot access the address for the selected location"

        binding.tvAddress.text = currentAddress
    }

    private fun registerAction() {
        binding.apply {
            btnDaftar.isEnabled = false

            edName.addTextChangedListener { validateInput() }
            edEmail.addTextChangedListener { validateInput() }
            edNomor.addTextChangedListener { validateInput() }
            edPassword.addTextChangedListener { validateInput() }
            edRepeatPassword.addTextChangedListener { validateInput() }

            btnDaftar.setOnClickListener {
                showLoading(true)
                if (edRepeatPassword.text.toString() == edPassword.text.toString()) {
                    viewModel.postRegister(
                        edName.text.toString(),
                        edEmail.text.toString(),
                        edPassword.text.toString(),
                        tvAddress.text.toString(),
                        addressLat,
                        addressLong,
                        edNomor.text.toString()
                    )
                } else {
                    Toast.makeText(this@SignupActivity, "Password tidak sama", Toast.LENGTH_SHORT)
                        .show()
                    showLoading(false)
                }
            }

            viewModel.regResponse.observe(this@SignupActivity) { response ->
                response?.let {
                    if (it.error) {
                        showLoading(false)
                        Toast.makeText(this@SignupActivity, it.message, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        setupAction()
                    }
                }
            }
        }
    }

    private fun setupAction() {
        AlertDialog.Builder(this).apply {
            setTitle("Success")
            setMessage("Akun berhasil dibuat. Login untuk melanjutkan!")
            setPositiveButton("Lanjut") { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    private fun validateInput() {
        binding.apply {
            val isNameValid = edName.isValidInput()
            val isEmailValid = edEmail.isValidInput()
            val isPasswordValid = edPassword.isValidInput()
            val isRepeatPasswordValid = edRepeatPassword.isValidInput()

            btnDaftar.isEnabled =
                isNameValid && isEmailValid && isPasswordValid && isRepeatPasswordValid
        }
    }

    private fun EditText.isValidInput(): Boolean {
        return length() > 0 && error.isNullOrEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}