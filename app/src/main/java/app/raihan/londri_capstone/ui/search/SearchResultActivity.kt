package app.raihan.londri_capstone.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivitySearchResultBinding

class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
//                    searchBar.text = searchView.text
                    searchView.hide()
                    false}
           // searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                // Handle menuItem click.
                when(menuItem.itemId){

                }

                true
            }
        }

        binding.backBtn.setOnClickListener{onBackPressed()}
    }
}