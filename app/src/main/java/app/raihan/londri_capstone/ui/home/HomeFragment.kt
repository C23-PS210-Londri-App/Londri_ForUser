package app.raihan.londri_capstone.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.response.DataProfileResponse
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.databinding.FragmentHomeBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.DetailLaundryActivity
import app.raihan.londri_capstone.ui.profile.ProfileActivity
import app.raihan.londri_capstone.ui.search.SearchResultActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var laundryAdapter: LaundryAdapter
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileImg.setOnClickListener {
            val moveIntent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(moveIntent)
        }

        binding.searchBar.setOnClickListener {
            val moveIntent = Intent(requireContext(), SearchResultActivity::class.java)
            startActivity(moveIntent)
        }

        binding.recAllLondri.layoutManager = LinearLayoutManager(requireContext())

        observe()
    }

    private fun observe () {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            viewModel.getProfile(user.token)
            viewModel.getAllLaundry(user.token)
        }

        viewModel.profileResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Result.Loading -> {

                }

                is Result.Error -> {

                }

                is Result.Success -> {

                    setUpProfileUI(response.data.response)
                }
            }
        }

        viewModel.allLaundryResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is Result.Loading -> {

                }

                is Result.Error -> {

                }

                is Result.Success -> {
                    setupAllLaundryUI(response.data.resultData)
                }
            }
        }
    }

    private fun setUpProfileUI(data: DataProfileResponse) {
        binding.apply {
            txtName.text = data.namaLengkap
            txtAlamat.text = data.alamat
        }
    }

    private fun setupAllLaundryUI(data: List<ResultDataItem>){
        binding.recAllLondri.layoutManager = LinearLayoutManager(requireContext())
        laundryAdapter = LaundryAdapter(data)
        binding.recAllLondri.adapter = laundryAdapter

        laundryAdapter.setOnClickListener(
            object : LaundryAdapter.OnClickListener{
                override fun onClick(position: Int, model: ResultDataItem) {
                    val moveIntent = Intent(requireContext(), DetailLaundryActivity::class.java)
                    startActivity(moveIntent)
                }
            }
        )
    }
}
