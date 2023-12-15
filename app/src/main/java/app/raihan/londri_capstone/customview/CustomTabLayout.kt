package app.raihan.londri_capstone.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.CustomTabLayoutBinding
import app.raihan.londri_capstone.databinding.CustomViewTabBinding
import com.google.android.material.tabs.TabLayout

class CustomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        CustomViewTabBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.apply {
            tabLayout.setSelectedTabIndicator(R.drawable.bg_tab_layout)
        }
    }

    fun getTabLayout(): TabLayout {
        return binding.tabLayout
    }
}