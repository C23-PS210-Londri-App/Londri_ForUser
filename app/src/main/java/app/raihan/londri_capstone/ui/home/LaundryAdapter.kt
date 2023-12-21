package app.raihan.londri_capstone.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.databinding.ItemAllLaundryBinding
import app.raihan.londri_capstone.ui.detail.DetailLaundryActivity
import app.raihan.londri_capstone.ui.detail.DetailLaundryActivity.Companion.EXTRA_ID
import com.bumptech.glide.Glide

class LaundryAdapter(private val listLaundry: List<ResultDataItem>) :
    RecyclerView.Adapter<LaundryAdapter.ListViewHolder>() {
    class ListViewHolder(private val homeBinding: ItemAllLaundryBinding) :
        RecyclerView.ViewHolder(homeBinding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: ResultDataItem) {

            homeBinding.apply {
                namaLaundry.text = data.namaLaundry
                Glide
                    .with(itemView)
                    .load(data.fotoLaundry)
                    .placeholder(R.drawable.placeholder_laundry)
                    .centerCrop()
                    .into(imgLaundry)
                statusLaundry.text = data.status
                alamat.text = data.alamat

                when (data.status) {
                    "Close" -> {
                        statusLaundry.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.red_700
                            )
                        )
                    }
                    else -> {
                        statusLaundry.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.colorBlue
                            )
                        )
                    }
                }
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailLaundryActivity::class.java)
                data.id.let {
                    intent.putExtra(EXTRA_ID, it.toString())
                    Log.d("DebugIDPUT:", it.toString())
                    itemView.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val laundryBinding =
            ItemAllLaundryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(laundryBinding)
    }

    override fun getItemCount(): Int = listLaundry.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listLaundry[position])
    }
}