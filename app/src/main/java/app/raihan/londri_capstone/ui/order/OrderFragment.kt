package app.raihan.londri_capstone.ui.order

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.FragmentOrderBinding
import app.raihan.londri_capstone.databinding.FragmentProfileBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.login.LoginActivity
import app.raihan.londri_capstone.ui.profile.EditProfileActivity
import app.raihan.londri_capstone.ui.profile.ProfileViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val viewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            user.token.let { token ->
                viewModel.getStatusProcess(token)
                setAdapter(token)
            }

        }


        viewModel.statusProcessResponse.observe(viewLifecycleOwner){data->
            binding.apply {

            }
        }

    }


    private fun setAdapter(token: String) {
        val sectionPageradapter = SectionPageradapter(requireContext())
        sectionPageradapter.token = token

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPageradapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text1,
            R.string.tab_text2
        )
    }
}