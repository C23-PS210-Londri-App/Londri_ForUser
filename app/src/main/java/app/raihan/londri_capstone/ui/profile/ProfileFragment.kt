package app.raihan.londri_capstone.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.FragmentProfileBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.login.LoginActivity
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            user.token.let { token ->
                viewModel.getProfile(token)
            }
        }
        viewModel.profileResponse.observe(viewLifecycleOwner){profile->
            binding.apply {
                tvEmail.text = profile[0].email
                tvAddress.text = profile[0].alamat
                noTelp.text = profile[0].nomorTelepon
                namaUser.text = profile[0].namaLengkap
                Glide
                    .with(this@ProfileFragment)
                    .load(profile[0].fotoProfil)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(ivProfile)
            }
        }

        binding.btnLogout.setOnClickListener({ logoutConfirmation() })
        binding.editProfileBtn.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            intent.putExtra("keyName", binding.namaUser.text)
            intent.putExtra("keyTelp", binding.noTelp.text)
            startActivity(intent)
        }
    }
    private fun logoutConfirmation() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.konfirmasi_logout))
            .setMessage(getString(R.string.desc_konfirmasi_logout))
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel.logout()
                dialog.dismiss()
                redirectToLoginActivity()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun redirectToLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}