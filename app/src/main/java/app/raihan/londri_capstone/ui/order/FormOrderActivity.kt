package app.raihan.londri_capstone.ui.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.databinding.ActivityFormOrderBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class FormOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormOrderBinding
    private val viewModel by viewModels<FormOrderViewModel> {
        ViewModelFactory.getInstance(this@FormOrderActivity)
    }

    val jasa = arrayOf("Dryclean", "Cuci Saja", "Komplit", "Setrika Saja")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val laundryId = intent.getSerializableExtra(LAUNDRY_ID) as String
        val laundryNameOrder = intent.getSerializableExtra(LAUNDRY_NAME) as String

        binding.laundryName.text = laundryNameOrder
        val spinner = findViewById<Spinner>(R.id.spinner_jasa)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,jasa)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(applicationContext, "Jasa yang dipilih adalah = "+jasa[position],Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.topAppBar.setOnClickListener{onBackPressed()}
        observeProfile()
        observeFormOrder(laundryId)
        orderAction()
    }

    private fun observeProfile () {
        viewModel.getSession().observe(this@FormOrderActivity) { user ->
            viewModel.getProfile(user.token)
        }
        viewModel.profileResponse.observe(this@FormOrderActivity) { response ->
            when (response) {
                is Result.Loading -> {

                }

                is Result.Error -> {

                }

                is Result.Success -> {


                    binding.txtName.text = response.data.response.namaLengkap
                    binding.txtPhone.text = response.data.response.nomorTelepon
                }
            }
        }
    }

    private fun observeFormOrder (laundryId: String) {
        viewModel.getSession().observe(this) { user ->
            viewModel.postOrderLaundry(user.token, laundryId)
        }
        viewModel.formOrderResponse.observe(this) { response ->
            when (response) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Error -> {
                    showLoading(false)
                }

                is Result.Success -> {
                    showLoading(true)
                }
            }
        }
    }

    private fun orderAction() {
        binding.apply {
            nextBtn.isEnabled = false

            total1EditText.addTextChangedListener {
                validateInput()
            }

            nextBtn.setOnClickListener {
                orderLaundry()
                showDialogOrderSucceed()
            }
        }
    }

    private fun validateInput() {
        binding.apply {
            val isTotalKgValid = total1EditText.length() > 0 && total1EditText.error.isNullOrEmpty()

            nextBtn.isEnabled = isTotalKgValid
        }
    }

    private fun orderLaundry() {
        binding.apply {
            viewModel.postOrderLaundry(
                total1EditText.text.toString(),
                edCatatan.text.toString()
            )
        }
    }

    private fun showDialogOrderSucceed() {
        AlertDialog.Builder(this).apply {
            setTitle("Status")
            setMessage("Order anda berhasil!")
            setPositiveButton("Okay") { _, _ ->
                val moveIntent = Intent(this@FormOrderActivity, OrderFragment::class.java)
                startActivity(moveIntent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showDialogOrderFailed(){
        AlertDialog.Builder(this).apply {
            setTitle("Status")
            setMessage("Order anda gagal dimuat, harap isi kolom berat pakaian dahulu!")
            create()
            show()
        }
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val LAUNDRY_ID = "LAUNDRY_ID"
        const val LAUNDRY_NAME = "LAUNDRY_NAME"
    }
}
