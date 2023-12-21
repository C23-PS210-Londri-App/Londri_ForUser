package app.raihan.londri_capstone.ui.order

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivityFormOrderBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.home.HomeViewModel

class FormOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormOrderBinding
    private val viewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(this)
    }

    var cekHargaSatuan: Int = 0
    var defaultBeratPakaian: Int = 0
    private lateinit var layananId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val laundryId = intent.getStringExtra("laundryId")
        val tokenSession = intent.getStringExtra("tokenSession")

        getProfilInfo(tokenSession)
        getLayananLaundry(tokenSession, laundryId)

    }


    private fun getLayananLaundry(tokenSession: String?, laundryId: String?) {
        if (laundryId != null && tokenSession != null) {
            viewModel.getLayananByLaundry(laundryId)


            binding.edBerat.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                @SuppressLint("SetTextI18n")
                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    binding.valueCekBerat.text = s

                    val beratString = s.toString()
                    if (beratString.isNotEmpty()) {
                        val beratInt = beratString.toInt()
                        val hargaSatuanString = binding.valueCekHarga.text.toString()

                        if (hargaSatuanString.isNotEmpty()) {
                            val hargaSatuan = hargaSatuanString.toInt()
                            updateHargaTotal(beratInt, hargaSatuan)
                        } else {
                            binding.valueCekHargaTotal.text = "0"
                        }
                    } else {
                        binding.valueCekHargaTotal.text = "0"
                    }
                }
            })

            var layananLength = 0
            viewModel.layananByLaundry.observe(this) { layananResponse ->

                if (!layananResponse[0].error) {
                    val layananList = layananResponse[0].resultData

                    val namaLayananList = layananList.map { it.namaLayanan }

                    layananLength = namaLayananList.size // cek jumlah layanan
                    if (layananLength == 0) {
                        binding.apply {
                            edBerat.visibility = View.GONE
                            edCatatan.visibility = View.GONE
                            btnProcess.isEnabled = false
                            tvcekBerat.visibility = View.GONE
                        }
                    } else {
                        binding.edBerat.visibility = View.VISIBLE
                        binding.edCatatan.visibility = View.VISIBLE
                        binding.btnProcess.isEnabled = true
                    }

                    Log.d("DebugLength", layananLength.toString())
                    val spinner = findViewById<Spinner>(R.id.spinnerJasa)

                    val arrayAdapter =
                        ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            namaLayananList
                        )
                    spinner.adapter = arrayAdapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val selectedLayanan = layananList[position]
                            layananId = selectedLayanan.id.toString()

                            binding.valueCekHarga.text = selectedLayanan.hargaLayanan.toString()
                            binding.tvcekNamaJasa.text = selectedLayanan.namaLayanan

                            cekHargaSatuan = selectedLayanan.hargaLayanan

                            val beratString = binding.valueCekBerat.text.toString()
                            defaultBeratPakaian = beratString.toInt()

                            updateHargaTotal(defaultBeratPakaian, cekHargaSatuan)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // Handle the case where nothing is selected
                        }
                    }
                } else {
                    Log.e("API Error", layananResponse[0].message)
                }
            }

            binding.btnProcess.setOnClickListener {
                val catatanProses = binding.edCatatan.text.toString()
                val beratProses = binding.valueCekBerat.text.toString()
                Log.d("BTN", "$tokenSession, $laundryId, $layananId, $beratProses, $catatanProses")

                viewModel.postOrder(tokenSession, laundryId, layananId, beratProses, catatanProses)
            }

            viewModel.orderResponse.observe(this@FormOrderActivity) { data ->
                Toast.makeText(
                    this@FormOrderActivity,
                    data.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateHargaTotal(defaultBeratPakaian: Int, cekHargaSatuan: Int) {
        binding.valueCekHargaTotal.text = (defaultBeratPakaian * cekHargaSatuan).toString()
    }


    private fun getProfilInfo(tokenSession: String?) {
        if (tokenSession != null) {
            viewModel.getProfile(tokenSession)
            viewModel.profileResponse.observe(this) { profile ->
                binding.apply {
                    tvNamaPemesan.text = profile[0].namaLengkap
                    tvTelpPemesan.text = profile[0].nomorTelepon
                    tvAddress.text = profile[0].alamat
                }
            }

        }
    }

//    private fun postOrder(
//        tokenSession: String,
//        laundryId: String,
//        layananId: String,
//        estimasiBerat: String,
//        catatan: String
//    ) {
//        binding.apply {
//            viewModel.postOrder(
//                tokenSession,
//                laundryId,
//                layananId,
//                estimasiBerat,
//                catatan
//            )
//        }
//
//        viewModel.orderResponse.observe(this@FormOrderActivity){data ->
//            Toast.makeText(
//                this@FormOrderActivity,
//                data.message,
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//
//    }
}