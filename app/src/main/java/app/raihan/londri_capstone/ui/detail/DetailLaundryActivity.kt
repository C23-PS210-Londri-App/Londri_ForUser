package app.raihan.londri_capstone.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.response.DataDetailLaundryResponse
import app.raihan.londri_capstone.databinding.ActivityDetailLaundryBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.order.FormOrderActivity
import com.bumptech.glide.Glide

class DetailLaundryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLaundryBinding
    private val viewModel by viewModels<DetailLaundryViewModel> {
        ViewModelFactory.getInstance(this@DetailLaundryActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLaundryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val laundryId = intent.getSerializableExtra(LAUNDRY_ID) as String
        val laundryNameOrder = intent.getSerializableExtra(LAUNDRY_NAME) as String
        observe(laundryId, laundryNameOrder )

        binding.topAppBar.setOnClickListener{onBackPressed()}
    }

    private fun observe (laundryId: String, laundryNameOrder: String) {
        viewModel.getSession().observe(this@DetailLaundryActivity) { user ->
            viewModel.getDetailLaundry(user.token, laundryId)
        }
        viewModel.detailLaundryResponse.observe(this@DetailLaundryActivity) { response ->
            when(response) {
                is Result.Loading -> {

                }

                is Result.Error -> {

                }

                is Result.Success -> {

                    setUpUI(response.data.resultData, laundryId, laundryNameOrder)
                }
            }
        }
    }

    private fun setUpUI(data: DataDetailLaundryResponse, laundryId: String, laundryNameOrder: String) {
        Glide
            .with(this)
            .load(data.fotoLaundry)
            .fitCenter()
            .into(binding.viewDetailLaundry)

        binding.apply {
            laundryName.text = data.namaLaundry
            status.text = data.status
            laundryAddress.text = data.alamat

            binding.orderBtn.setOnClickListener {
                val moveIntent = Intent(this@DetailLaundryActivity, FormOrderActivity::class.java)
                startActivity(moveIntent, Bundle().apply {
                    putSerializable(LAUNDRY_ID, laundryId)
                    putSerializable(LAUNDRY_NAME, data.namaLaundry)
                    moveIntent.putExtras(this)
                    finish()
                })
            }
        }
    }

    companion object{
        const val LAUNDRY_ID = "LAUNDRY_ID"
        const val LAUNDRY_NAME = "LAUNDRY_NAME"
    }
}