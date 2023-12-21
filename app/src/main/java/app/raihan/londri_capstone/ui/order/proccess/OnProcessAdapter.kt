package app.raihan.londri_capstone.ui.order.proccess

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.data.response.ResultProcessItem
import app.raihan.londri_capstone.databinding.ItemOrderBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class OnProcessAdapter(private val listOrderData: List<ResultProcessItem>) :
    RecyclerView.Adapter<OnProcessAdapter.ListViewHolder>() {
    class ListViewHolder(private val homeBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(homeBinding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: ResultProcessItem) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("dd MMMM yyyy")
            val outputFormatTime = SimpleDateFormat("HH:mm")

            homeBinding.apply {
                noPesanan.text = data.orderTrx
                jmlBerat.text = "${data.estimasiBerat} Kg"
                totalHarga.text = "Rp ${data.hargaTotal}"
                catatan.text = data.catatan
                tvStatus.text = data.status

                val statusOrder = data.status.lowercase(Locale.ROOT)

                when (statusOrder) {
                    "menunggu diterima" -> {
                        filler.setCardBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.colorBlue
                            )
                        )
                        Log.d("DebugColor", statusOrder)
                    }

                    "selesai" -> {
                        filler.setCardBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.colorGreen
                            )
                        )
                        Log.d("DebugColor", statusOrder)
                    }

                    else -> {
                        filler.setCardBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.colorYellow
                            )
                        )
                    }
                }

                val parseDate = inputFormat.parse(data.createdAt)
                parseDate.let { outputFormat }
                tglPesanan.text = parseDate?.let { outputFormat.format(it) }

                val parseTime = inputFormat.parse(data.createdAt)
                parseTime.let { outputFormatTime }
                jamOrder.text = parseTime?.let { outputFormatTime.format(it) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val homeBinding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(homeBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listOrderData[position])
    }

    override fun getItemCount(): Int = listOrderData.size

}