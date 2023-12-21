package app.raihan.londri_capstone.ui.order.proccess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.FragmentOnProccessBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.home.LaundryAdapter
import app.raihan.londri_capstone.ui.order.OrderViewModel

class OnProccessFragment(token: String, private var position: Int) : Fragment() {
    private lateinit var binding: FragmentOnProccessBinding
    private val viewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var token = token

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnProccessBinding.inflate(inflater, container, false)

        showOnProcessData(token)
        return binding.root

    }

    private fun showOnProcessData(token: String) {
        viewModel.getStatusProcess(token)

        viewModel.statusProcessResponse.observe(viewLifecycleOwner){data ->

            val layoutManager = LinearLayoutManager(requireActivity())
            binding.rvPesanan.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
            binding.rvPesanan.addItemDecoration(itemDecoration)

            if (data.isNullOrEmpty()) {
                binding.ivEmptyOrder.visibility = View.VISIBLE
            } else {
                binding.ivEmptyOrder.visibility = View.GONE
                binding.apply {
                    rvPesanan.adapter = OnProcessAdapter(data)
                }
            }
        }
    }


}
