package app.raihan.londri_capstone.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(HomeFragment())

        binding.apply {
            bottomNavigation.setOnItemReselectedListener {

                when(it.itemId) {

                    R.id.home -> {
                        setCurrentFragment(HomeFragment())
                        true
                    }
                    R.id.search -> {
                        setCurrentFragment(SearchFragment())
                        true
                    }
                    R.id.order -> {
                        setCurrentFragment(OrderFragment())
                        true
                    }
                    else -> true
                }
            }
        }
    }

    private fun setCurrentFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment)
        transaction.commit()
    }
}