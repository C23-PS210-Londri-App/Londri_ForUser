package app.raihan.londri_capstone.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.response.ResultRiwayatItem
import app.raihan.londri_capstone.databinding.FragmentOrderBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.home.LaundryAdapter
import app.raihan.londri_capstone.ui.main.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OrderFragment : Fragment() {

    private lateinit var bindingOrder: FragmentOrderBinding
    private lateinit var onProcessLaundryAdapter: OnProcessLaundryAdapter
    private val viewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingOrder = FragmentOrderBinding.inflate(inflater, container, false)

        return bindingOrder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingOrder.apply {
            val tabLayout = tabLayout.getTabLayout()
            tabLayout.apply {
                addTab(tabLayout.newTab().setText("Sedang Berjalan"))
                addTab(tabLayout.newTab().setText("       Selesai       "))
                addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        when(tab?.position){
                            0 -> {
                                observeOnProcessLaundryList()
                            }
                            1 -> {

                            }
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                })
            }
        }
    }

    private fun observeOnProcessLaundryList(){
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            viewModel.getOnProcessLaundry(user.token)
            viewModel.onProcessLaundryResponse.observe(viewLifecycleOwner) { response ->
                when(response) {
                    is Result.Loading -> {

                    }
                    is Result.Error -> {

                    }

                    is Result.Success -> {

                        setUpOnProcessUI(response.data.resultRiwayat)
                    }
                }
            }
        }
    }

    private fun setUpOnProcessUI(data: List<ResultRiwayatItem>){
        bindingOrder.rvHistoryOrder.layoutManager = LinearLayoutManager(requireContext())
        onProcessLaundryAdapter = OnProcessLaundryAdapter(data)
        bindingOrder.rvHistoryOrder.adapter = onProcessLaundryAdapter
    }
}