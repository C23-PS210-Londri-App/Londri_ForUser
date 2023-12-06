package app.raihan.londri_capstone.ui.main

import android.app.LauncherActivity.ListItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListAdapter
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var bindingSignIn: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bindingSignIn = FragmentHomeBinding.inflate(inflater, container, false)

        return bindingSignIn.root

    }
}