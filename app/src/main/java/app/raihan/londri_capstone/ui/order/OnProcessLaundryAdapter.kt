package app.raihan.londri_capstone.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.data.response.ResultRiwayatItem
import app.raihan.londri_capstone.databinding.ItemAllLaundryBinding
import app.raihan.londri_capstone.databinding.ItemOrderBinding
import com.bumptech.glide.Glide

class OnProcessLaundryAdapter (private val itemLaundry: List<ResultRiwayatItem>) : RecyclerView.Adapter<OnProcessLaundryAdapter.ListViewHolder>() {

    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder){
            with(itemLaundry[position]){
                binding.noPesanan.text = this.orderTrx
                itemView.setOnClickListener {
                    if (onClickListener != null) {
                        onClickListener!!.onClick(position, this )
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = itemLaundry.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: ResultRiwayatItem)
    }


    inner class ListViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)
}