package app.raihan.londri_capstone.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.databinding.ItemAllLaundryBinding
import com.bumptech.glide.Glide

class LaundryAdapter (private val itemLaundry: List<ResultDataItem>) : RecyclerView.Adapter<LaundryAdapter.ListViewHolder>() {

    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemAllLaundryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder){
            with(itemLaundry[position]){
                binding.namaLaundry.text = this.namaLaundry
                Glide.with(itemView).load(this.fotoLaundry).into(binding.imgLaundry)
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
        fun onClick(position: Int, model: ResultDataItem)
    }


    inner class ListViewHolder(val binding: ItemAllLaundryBinding) : RecyclerView.ViewHolder(binding.root)
}