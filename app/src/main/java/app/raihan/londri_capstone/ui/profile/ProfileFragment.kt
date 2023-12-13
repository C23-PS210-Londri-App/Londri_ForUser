package app.raihan.londri_capstone.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.raihan.londri_capstone.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var bindingSignIn: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingSignIn = FragmentProfileBinding.inflate(inflater, container, false)

        return bindingSignIn.root
    }
}