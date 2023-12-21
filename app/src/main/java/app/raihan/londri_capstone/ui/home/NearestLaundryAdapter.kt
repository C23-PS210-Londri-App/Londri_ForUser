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
import app.raihan.londri_capstone.data.response.ResultNearestDataItem
import app.raihan.londri_capstone.databinding.ItemAllLaundryBinding
import app.raihan.londri_capstone.databinding.ItemRecommendBinding
import app.raihan.londri_capstone.databinding.ItemRecommendLaundryBinding
import app.raihan.londri_capstone.ui.detail.DetailLaundryActivity
import com.bumptech.glide.Glide

class NearestLaundryAdapter(private val listLaundry: List<ResultNearestDataItem>) :
    RecyclerView.Adapter<NearestLaundryAdapter.ListViewHolder>() {
    class ListViewHolder(private val homeBinding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(homeBinding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: ResultNearestDataItem) {

            homeBinding.apply {

                val namaLaundry = data.namaLaundry
                val maxLength = 20

                val pressNamaLaundry = if (namaLaundry.length > maxLength) {
                    namaLaundry.substring(0, maxLength) + "..."
                } else {
                    namaLaundry
                }


                tvNamaLaundry.text = pressNamaLaundry
                tvStatusLaundry.text = data.status
                tvJarakLaundry.text = "${data.distance} km"
                Glide
                    .with(itemView)
                    .load(data.fotoLaundry)
                    .placeholder(R.drawable.placeholder_laundry)
                    .centerCrop()
                    .into(imgLaundry)

                when (data.status) {
                    "Close" -> {
                        tvStatusLaundry.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.red_700
                            )
                        )
                    }
                    else -> {
                        tvStatusLaundry.setTextColor(
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
                    intent.putExtra(DetailLaundryActivity.EXTRA_ID, it.toString())
                    Log.d("DebugIDPUT:", it.toString())
                    itemView.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val laundryBinding =
            ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(laundryBinding)
    }

    override fun getItemCount(): Int = listLaundry.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listLaundry[position])
    }
}