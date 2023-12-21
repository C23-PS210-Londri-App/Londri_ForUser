package app.raihan.londri_capstone.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.FragmentHomeBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var addressLat: String
    private lateinit var addressLong: String
    private lateinit var locationManager: LocationManager
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the LocationManager
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Check and request location permissions using registerForActivityResult
        getCurrentLocation()

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            user.token.let { token ->
                viewModel.getProfile(token)
            }
        }

        viewModel.profileResponse.observe(viewLifecycleOwner) { profile ->
            binding.apply {
                txtName.text = profile[0].namaLengkap
                txtAlamat.text = profile[0].alamat

                Glide
                    .with(this@HomeFragment)
                    .load(profile[0].fotoProfil)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(profileImg)

            }
        }

        loadDataLaundry()
        searchData()
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getCurrentLocation()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getCurrentLocation()
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Location permission denied. Unable to get current location.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getCurrentLocation() {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        try {
            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
                checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        getCurrentAddress(requireContext(), location.latitude, location.longitude)
                    } else {
                        Toast.makeText(
                            requireContext(),
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
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun getCurrentAddress(context: Context, lat: Double, long: Double): String {
        val geocode = Geocoder(context, Locale.getDefault())
        val listAddress = geocode.getFromLocation(lat, long, 1)
        val currentAddress = listAddress?.get(0)?.getAddressLine(0)
        currentAddress ?: "Cannot access your address"

        addressLat = lat.toString()
        addressLong = long.toString()

        loadNearestLaundry()

        return currentAddress ?: "Cannot access your address"
    }

    private fun loadNearestLaundry() {
        binding.recRekomendasi.adapter = null
        binding.tvTitleRekom.visibility = View.VISIBLE
        binding.tvDescRekom.visibility = View.VISIBLE

        viewModel.getNearestLaundry(addressLat, addressLong)
        binding.recRekomendasi.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        viewModel.nearestLaundryResponse.observe(viewLifecycleOwner) { listData ->
            if (listData != null) {
                binding.recRekomendasi.adapter = NearestLaundryAdapter(listData)
            } else {
                binding.recRekomendasi.adapter = null
            }
        }
    }

    private fun loadDataLaundry() {
        // Reset Recycler
        binding.recAllLondri.adapter = null
        binding.recRekomendasi.adapter = null

        binding.tvAllLondri.text = "Semua Londri"

        viewModel.getAllLaundry()

        binding.recAllLondri.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }


        viewModel.laundryResponse.observe(viewLifecycleOwner) { listData ->
            if (listData != null) {
                binding.recAllLondri.adapter = LaundryAdapter(listData)
            } else {
                binding.recAllLondri.adapter = null
            }
        }
    }


    private fun searchData() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    // Reset Recycler
                    binding.recAllLondri.adapter = null
                    binding.recRekomendasi.adapter = null
                    binding.tvTitleRekom.visibility = View.GONE
                    binding.tvDescRekom.visibility = View.GONE

                    binding.tvAllLondri.text = "Hasil Pencarian"

                    viewModel.getSearch(query.toString())

                    binding.recAllLondri.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                    }

                    viewModel.listSearchItem.observe(viewLifecycleOwner) { data ->
                        binding.recAllLondri.adapter = data?.let { HomeSearchAdapter(it) }
                    }



                    binding.searchBar.clearFocus()
                } else {
                    loadDataLaundry()
                    loadNearestLaundry()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    loadDataLaundry()
                    loadNearestLaundry()
                }
                return true
            }
        })
    }
}
