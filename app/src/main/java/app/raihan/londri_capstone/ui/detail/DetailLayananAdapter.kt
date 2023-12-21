package app.raihan.londri_capstone.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.data.response.DataDetailLaundryResponse
import app.raihan.londri_capstone.data.response.LayananByLaundryResponse
import app.raihan.londri_capstone.data.response.LayananDetailItem
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.data.response.ResultDataLayananByLaundryItem
import app.raihan.londri_capstone.databinding.ItemAllLaundryBinding
import app.raihan.londri_capstone.databinding.ItemLayananBinding
import app.raihan.londri_capstone.ui.home.LaundryAdapter
import com.bumptech.glide.Glide

class DetailLayananAdapter (private val listLaundry: List<ResultDataLayananByLaundryItem>) :
    RecyclerView.Adapter<DetailLayananAdapter.ListViewHolder>() {
    class ListViewHolder(private val homeBinding: ItemLayananBinding) :
        RecyclerView.ViewHolder(homeBinding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: ResultDataLayananByLaundryItem) {

            homeBinding.apply {
                tvNamaLayanan.text = data.namaLayanan


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val laundryBinding =
            ItemLayananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(laundryBinding)
    }

    override fun getItemCount(): Int = listLaundry.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listLaundry[position])
    }
}