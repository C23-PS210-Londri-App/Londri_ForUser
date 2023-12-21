package app.raihan.londri_capstone.ui.order

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.raihan.londri_capstone.ui.order.done.DoneFragment
import app.raihan.londri_capstone.ui.order.proccess.OnProccessFragment

class SectionPageradapter(context: Context) : FragmentStateAdapter(context as FragmentActivity) {
    lateinit var token: String
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = OnProccessFragment(token, position)
            1 -> fragment = DoneFragment(token, position)
        }
        return fragment as Fragment
    }
}