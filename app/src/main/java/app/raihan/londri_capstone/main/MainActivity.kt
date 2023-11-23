package app.raihan.londri_capstone.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.apply {
            bottomNavigation.setOnClickListener {

                when(it.id) {

                    R.id.home -> replaceFragment(HomeFragment())
                    R.id.search -> replaceFragment(SearchFragment())
                    R.id.order -> replaceFragment(OrderFragment())

                    else -> {

                    }
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.commit()
    }
}