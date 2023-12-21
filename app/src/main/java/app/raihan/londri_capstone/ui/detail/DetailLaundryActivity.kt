package app.raihan.londri_capstone.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivityDetailLaundryBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.home.HomeSearchAdapter
import app.raihan.londri_capstone.ui.order.FormOrderActivity
import com.bumptech.glide.Glide

class DetailLaundryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailLaundryBinding
    private lateinit var laundryId: String

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    var dataToken = "Null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        laundryId = intent.getStringExtra(EXTRA_ID).orEmpty()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        getDetailLaundry(laundryId)
    }

    @SuppressLint("ResourceAsColor")
    private fun getDetailLaundry(laundryId: String) {
        viewModel.getSession().observe(this) { user ->
            user.token.let { token ->
                viewModel.getDetailLaundry(token, laundryId)
                viewModel.getLayananByLaundry(laundryId)
                Log.d("DebugToken:", token)

                dataToken = token
            }
        }


        viewModel.detailResponse.observe(this) { laundry ->
            Log.d("DebugDetail", "$laundry")
            binding.apply {
                tvNamaLaundry.text = laundry[0].namaLaundry
                tvStatus.text = laundry[0].status
                tvAlamat.text = laundry[0].alamat


                Glide
                    .with(this@DetailLaundryActivity)
                    .load(laundry[0].fotoLaundry)
                    .placeholder(R.drawable.placeholder_laundry)
                    .centerCrop()
                    .into(ivLaundry)


                val layananList = laundry[0].layanan

                val namaLayananList = layananList.map { it.namaLayanan }

                val layananLength = namaLayananList.size // cek jumlah layanan

                when (laundry[0].status) {
                    "Close" -> {
                        tvStatus.setTextColor(
                            ContextCompat.getColor(
                                this@DetailLaundryActivity,
                                R.color.red_700
                            )
                        )
                        addOrder.isEnabled = false
                        addOrder.backgroundTintList = ContextCompat.getColorStateList(
                            this@DetailLaundryActivity,
                            R.color.black_100
                        )
                    }

                    else -> {
                        tvStatus.setTextColor(
                            ContextCompat.getColor(
                                this@DetailLaundryActivity,
                                R.color.colorBlue
                            )
                        )
                        addOrder.isEnabled = true
                        addOrder.backgroundTintList = ContextCompat.getColorStateList(
                            this@DetailLaundryActivity,
                            R.color.blue_500
                        )
                    }
                }

                binding.addOrder.setOnClickListener {
                    if (layananLength != 0) {
                        val intent =
                            Intent(this@DetailLaundryActivity, FormOrderActivity::class.java)
                        intent.putExtra("laundryId", laundryId)
                        intent.putExtra("tokenSession", dataToken)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@DetailLaundryActivity,
                            "Layanan Belum Tersedia",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        }

        viewModel.insideLayananByLaundry.observe(this) { data ->
            binding.rvLayanan.apply {
                layoutManager = GridLayoutManager(this@DetailLaundryActivity, 2)
                setHasFixedSize(true)
            }
            binding.rvLayanan.adapter = data?.let { DetailLayananAdapter(it) }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}