package app.raihan.londri_capstone.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.raihan.londri_capstone.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private lateinit var bindingSignIn: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingSignIn = FragmentOrderBinding.inflate(inflater, container, false)

        return bindingSignIn.root
    }
}