package app.raihan.londri_capstone.ui.order

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.raihan.londri_capstone.ui.order.done.HistoryFragment
import app.raihan.londri_capstone.ui.order.proccess.OnProccessFragment

class OrderPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnProccessFragment()
            1 -> HistoryFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
