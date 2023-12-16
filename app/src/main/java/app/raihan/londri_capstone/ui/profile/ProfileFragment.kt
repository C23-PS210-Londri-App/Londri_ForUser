package app.raihan.londri_capstone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.response.DataProfileResponse
import app.raihan.londri_capstone.databinding.FragmentProfileBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editProfileBtn.setOnClickListener {
            val moveIntent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(moveIntent)
        }

        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
            Toast.makeText(requireContext(), "Berhasil logout!", Toast.LENGTH_LONG).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        observe()
    }
    private fun observe () {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            viewModel.getProfile(user.token)
        }

        viewModel.profileResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Result.Loading -> {

                }

                is Result.Error -> {

                }

                is Result.Success -> {

                    setUpUI(response.data.response)
                }
            }
        }
    }

    private fun setUpUI(data: DataProfileResponse) {
        binding.apply {
            namaUser.text = data.namaLengkap
            nomorUser.text = data.nomorTelepon
            emailUser.text = data.email
            alamatUser.text = data.alamat
        }
    }
}