package com.neppplus.keepthetime_20220816.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.neppplus.keepthetime_20220816.fragments.InvitedAppointmentFragment
import com.neppplus.keepthetime_20220816.fragments.MyAppointmentFragment
import com.neppplus.keepthetime_20220816.fragments.SettingFragment

class MainViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyAppointmentFragment()
            1 -> InvitedAppointmentFragment()
            else -> SettingFragment()
        }
    }
}